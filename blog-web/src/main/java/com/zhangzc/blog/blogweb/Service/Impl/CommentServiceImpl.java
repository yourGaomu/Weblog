package com.zhangzc.blog.blogweb.Service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhangzc.blog.blogcommon.Service.TCommentService;
import com.zhangzc.blog.blogcommon.Utils.CommentUtil;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.Utils.TimeUtil;
import com.zhangzc.blog.blogcommon.enums.CommentStatusEnum;
import com.zhangzc.blog.blogcommon.enums.ResponseCodeEnum;
import com.zhangzc.blog.blogcommon.exception.exceptionClasses.BizException;
import com.zhangzc.blog.blogcommon.exception.exceptionClasses.isCommentExamineOpenException;
import com.zhangzc.blog.blogcommon.exception.exceptionClasses.isCommentSensiException;
import com.zhangzc.blog.blogcommon.pojo.domain.TComment;
import com.zhangzc.blog.blogweb.Pojo.Vo.FindCommentItemRspVO;
import com.zhangzc.blog.blogweb.Pojo.Vo.FindCommentListRspVO;
import com.zhangzc.blog.blogweb.Pojo.Vo.FindQQUserInfoRspVO;
import com.zhangzc.blog.blogweb.Pojo.Vo.PublishCommentReqVO;
import com.zhangzc.blog.blogweb.Service.CommentService;
import com.zhangzc.blog.blogweb.Utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestTemplate;
import toolgood.words.IllegalWordsSearch;
import toolgood.words.IllegalWordsSearchResult;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final TCommentService tCommentService;
    private final RestTemplate restTemplate;
    @Value("${qq.apiKey}")
    private String apiKey;
    private final IllegalWordsSearch wordsSearch;

    @Override
    public R findQQUserInfo(Map<String, String> findQQUserInfoReqVO) {
        String qqNumber = findQQUserInfoReqVO.get("qq");
        //检验是不是纯数字
        if (!StringUtil.isPureNumber(qqNumber)) {
            return R.Faile("对不起，输入的QQ号有误");
        }
        //发送请求链接
        String url = String.format("https://api.nsmao.net/api/qq/query?qq=%s&key=%s", qqNumber, apiKey);
        try {
            //发送HTTP请求
            String result = restTemplate.getForObject(url, String.class);
            log.info(result);

            if (result == null) {
                return R.Faile("API返回结果为空");
            }
            // 使用ObjectMapper解析JSON
            ObjectMapper objectMapper = new ObjectMapper();
            // 解析JSON（注意JSON中的data字段）
            Map<String, Object> root = objectMapper.readValue(result, Map.class);
            Map<String, Object> data = (Map<String, Object>) root.get("data");
            // 3. 转换为目标实体类
            FindQQUserInfoRspVO userInfo = new FindQQUserInfoRspVO();

            userInfo.setNickname((String) data.get("nick"));
            userInfo.setAvatar((String) data.get("avatar"));
            userInfo.setMail((String) data.get("email"));
            return R.Success("查询成功", userInfo);
        } catch (JsonProcessingException e) {
            throw new BizException(ResponseCodeEnum.SERCH_QQNUMBER_FAILE);
        }
    }

    @Override
    @Transactional(rollbackFor = isCommentSensiException.class)
    public R publishComment(PublishCommentReqVO publishCommentReqVO) throws isCommentExamineOpenException, isCommentSensiException {
        TComment comment = new TComment();
        BeanUtils.copyProperties(publishCommentReqVO, comment);
        comment.setCreateTime(TimeUtil.getDateTime(LocalDate.now()));

        Integer isCommentSensiWordOpen = CommentUtil.getIsCommentSensiWordOpen();
        Integer isCommentExamineOpen = CommentUtil.getIsCommentExamineOpen();

        setExamcation(comment, isCommentExamineOpen, isCommentSensiWordOpen);
        if (comment.getStatus().equals(3)) {
            // 手动标记回滚并抛出异常
            //说明存在敏感词
            throw new isCommentSensiException(ResponseCodeEnum.COMMENT_CONTAIN_SENSITIVE_WORD);
        } else if (comment.getStatus().equals(1)) {
            // 不标记回滚，直接抛出异常
            //说明开启了审核功能
            //先保存再抛出异常
            tCommentService.save(comment);
            throw new isCommentExamineOpenException(ResponseCodeEnum.COMMENT_WAIT_EXAMINE);
        }
        return R.Success("保存成功");
    }

    @Override
    public R findCommentList(Map<String, String> findCommentListReqVO) {
        String routerUrl = findCommentListReqVO.get("routerUrl");
        //查询该路由下的正常能显示的评论
        List<TComment> comments = tCommentService.lambdaQuery()
                .eq(TComment::getRouterUrl, routerUrl)
                .orderByDesc(TComment::getCreateTime)
                .eq(TComment::getStatus, CommentStatusEnum.NORMAL.getCode())
                .list();
        if (comments == null || comments.isEmpty()) {
            return R.Success("没有评论存在", null);
        }

        FindCommentListRspVO result = new FindCommentListRspVO();
        result.setTotal(comments.size());
        // 构建评论ID到评论对象的映射
        Map<Long, TComment> commentMap = comments.stream()
                .collect(Collectors.toMap(
                        TComment::getId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));
        // 提取一级评论
        List<FindCommentItemRspVO> primaryComments = comments.stream()
                .filter(comment -> comment.getParentCommentId() == null)
                .map(comment -> {
                    FindCommentItemRspVO vo = new FindCommentItemRspVO();
                    BeanUtils.copyProperties(comment, vo);
                    vo.setReplyNickname(null);
                    return vo;
                })
                .collect(Collectors.toList());
        // 按父评论ID分组子评论，并按创建时间升序排序
        //TODO 这里有问题
        Map<Long, List<FindCommentItemRspVO>> childCommentMap = comments.stream()
                .filter(comment -> comment.getParentCommentId() != null) //过滤了一级评论
                .map(comment -> {
                    FindCommentItemRspVO vo = new FindCommentItemRspVO();
                    BeanUtils.copyProperties(comment, vo);
                    vo.setParentCommentId(comment.getParentCommentId());
                    vo.setCreateTime(comment.getCreateTime());
                    // 设置回复人昵称
                    if (comment.getReplyCommentId() != null &&
                            !comment.getReplyCommentId().equals(comment.getParentCommentId())) {
                        TComment replyToComment = commentMap.get(comment.getReplyCommentId());
                        if (replyToComment != null) {
                            vo.setReplyNickname(replyToComment.getNickname());
                        }
                    }
                    return vo;
                })
                .collect(Collectors.groupingBy(
                        FindCommentItemRspVO::getParentCommentId,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(FindCommentItemRspVO::getCreateTime))
                                        .collect(Collectors.toList())
                        )
                ));
        // 为一级评论设置子评论
        primaryComments.forEach(vo -> {
            List<FindCommentItemRspVO> children = childCommentMap.getOrDefault(vo.getId(), Collections.emptyList());
            if (!children.isEmpty()) {
                setChildComments(vo, children);
            }
        });
        result.setComments(primaryComments);
        return R.Success("查找成功", result);
    }

    private void setChildComments(FindCommentItemRspVO vo, List<FindCommentItemRspVO> comments) {
        vo.setChildComments(comments);

    }

    private void setExamcation(TComment comment, Integer isCommentExamineOpen, Integer isCommentSensiWordOpen) {
        if (isCommentExamineOpen.equals(0) && isCommentSensiWordOpen.equals(0)) {
            comment.setStatus(CommentStatusEnum.NORMAL.getCode());
            comment.setReason(CommentStatusEnum.NORMAL.getDescription());
            return;
        }

        if (isCommentSensiWordOpen.equals(1)) {
            //开启了过滤词
            // 校验评论中是否包含敏感词
            boolean b = wordsSearch.ContainsAny(comment.getContent());
            if (b) {
                //存在有敏感词
                //查找有哪些敏感词
                List<IllegalWordsSearchResult> results = wordsSearch.FindAll(comment.getContent());
                List<String> keywords = results.stream()
                        .map(result -> result.Keyword)
                        .collect(Collectors.toList());
                //设置状态码为3，说明有敏感词
                comment.setStatus(CommentStatusEnum.EXAMINE_FAILED.getCode());
                //设置原因
                String reason = String.format("系统自动拦截，包含敏感词：%s", keywords);
                //提示一下
                log.warn("此评论内容中包含敏感词: {}, content: {}", keywords, comment.getContent());
                comment.setReason(reason);
                return;
            }
        }
        if (isCommentExamineOpen.equals(1)) {
            //开启了评论审核
            //设置状态码为1，等待审核
            comment.setStatus(CommentStatusEnum.WAIT_EXAMINE.getCode());
            comment.setReason(CommentStatusEnum.WAIT_EXAMINE.getDescription());
        }
    }
}
