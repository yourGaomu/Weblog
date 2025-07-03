package com.zhangzc.blog.blogadmin.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangzc.blog.blogadmin.Pojo.Vo.ExamineCommentReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindCommentPageListReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindCommentPageListRspVO;
import com.zhangzc.blog.blogadmin.Service.AdminCommentService;
import com.zhangzc.blog.blogcommon.Service.TCommentService;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.Utils.TimeUtil;
import com.zhangzc.blog.blogcommon.pojo.domain.TComment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {
    private final TCommentService tCommentService;

    @Override
    public PageResult findCommentPageList(FindCommentPageListReqVO findCommentPageListReqVO) {
        IPage<TComment> page = new Page<>(findCommentPageListReqVO.getCurrent(), findCommentPageListReqVO.getSize());
        LambdaQueryWrapper<TComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(findCommentPageListReqVO.getRouterUrl() != null, TComment::getRouterUrl, findCommentPageListReqVO.getRouterUrl())
                .gt(findCommentPageListReqVO.getStartDate() != null, TComment::getCreateTime, findCommentPageListReqVO.getStartDate())
                .lt(findCommentPageListReqVO.getEndDate() != null, TComment::getCreateTime, findCommentPageListReqVO.getEndDate())
                .eq(findCommentPageListReqVO.getStatus() != null, TComment::getStatus, findCommentPageListReqVO.getStatus())
                .orderByAsc(TComment::getCreateTime);
        IPage<TComment> page1 = tCommentService.page(page, wrapper);
        List<TComment> records1 = page1.getRecords();

        List<FindCommentPageListRspVO> records = page1.getRecords().stream().map(sign -> {
            FindCommentPageListRspVO vo = new FindCommentPageListRspVO();
            BeanUtils.copyProperties(sign, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.Success(page1, records);
    }

    @Override
    public R deleteComment(Map<String, String> deleteCommentReqVO) {
        Long commentID = Long.valueOf(deleteCommentReqVO.get("id"));
        boolean b = tCommentService.removeById(commentID);
        if (b) {
            return R.Success("删除评论成功");
        }
        return R.Faile("删除失败请重试");
    }

    @Override
    public R examine(ExamineCommentReqVO examineCommentReqVO) {
        Long id = examineCommentReqVO.getId();
        if (id == null) {
            return R.Success("评论不存在");
        }
        TComment byId = tCommentService.getById(id);
        if (byId == null) {
            return R.Success("评论不存在");
        }
        boolean update = tCommentService.lambdaUpdate()
                .eq(TComment::getId, id)
                .set(TComment::getReason, "审核通过")
                .set(TComment::getUpdateTime, TimeUtil.getDateTime(LocalDate.now()))
                .set(TComment::getStatus, 2)
                .update();
        if (update) {
            return R.Success("审核成功");
        }
        return R.Faile("审核失败请重试");
    }
}
