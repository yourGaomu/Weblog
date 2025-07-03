package com.zhangzc.blog.blogadmin.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindTagArticlePageListReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindTagArticlePageListRspVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindTagListRspVO;
import com.zhangzc.blog.blogadmin.Service.TagService;
import com.zhangzc.blog.blogcommon.Service.TArticleService;
import com.zhangzc.blog.blogcommon.Service.TArticleTagRelService;
import com.zhangzc.blog.blogcommon.Service.TTagService;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.pojo.domain.TArticle;
import com.zhangzc.blog.blogcommon.pojo.domain.TArticleTagRel;
import com.zhangzc.blog.blogcommon.pojo.domain.TTag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TArticleTagRelService tArticleTagRelService;
    private final TTagService tTagService;
    private final TArticleService tArticleService;

    @Override
    public R findTagList() {
        List<FindTagListRspVO> collect = tTagService.list().stream()
                .map(record -> {
                    FindTagListRspVO vo = new FindTagListRspVO();
                    BeanUtils.copyProperties(record, vo);
                    return vo;
                }).collect(Collectors.toList());
        return R.Success("查找成功", collect);
    }

    @Override
    public PageResult findTagPageList(FindTagArticlePageListReqVO findTagArticlePageListReqVO) {
        IPage<TArticleTagRel> page = new Page<>(findTagArticlePageListReqVO.getCurrent()
                , findTagArticlePageListReqVO.getSize());
        //构建查询的条件
        LambdaQueryWrapper<TArticleTagRel> queryWrapper = new LambdaQueryWrapper<TArticleTagRel>();
        queryWrapper.eq(TArticleTagRel::getTagId, findTagArticlePageListReqVO.getId());
        //获取查询之后的结果
        IPage<TArticleTagRel> page1 = tArticleTagRelService.page(page, queryWrapper);
        List<TArticleTagRel> records = page1.getRecords();
        if (records == null || records.size() == 0) {
            return PageResult.Success(page1);
        }
        //获取文章的ID集合
        List<Long> collect = records.stream().map(TArticleTagRel::getArticleId).collect(Collectors.toList());
        //查询文章
        List<FindTagArticlePageListRspVO> collect1 = tArticleService.lambdaQuery()
                .in(TArticle::getId, collect)
                .list().stream()
                .map(sign -> {
                    FindTagArticlePageListRspVO vo = new FindTagArticlePageListRspVO();
                    BeanUtils.copyProperties(sign, vo);
                    vo.setCreateDate(sign.getCreateTime().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate());
                    return vo;
                }).collect(Collectors.toList());
        return PageResult.Success(page1, collect1);
    }

    @Override
    //@Cacheable(cacheNames = "Tags",key = "#findTagArticlePageListReqVO['size']")
    public R findTagList(Map<String, String> findTagArticlePageListReqVO) {
        List<TTag> tagDOS = null;
        // 获取 size 参数
        if (findTagArticlePageListReqVO!=null && findTagArticlePageListReqVO.size()>0) {
            Long size = Long.valueOf(findTagArticlePageListReqVO.get("size").toString());
            // 查询标签
            if (Objects.isNull(size) || size == 0) {
                tagDOS = tTagService.lambdaQuery().list();// 查询全部
            } else {
                Page<TTag> page = new Page<>(1, size);
                tagDOS = tTagService.page(page).getRecords();
            }
        }else {
            tagDOS = tTagService.lambdaQuery().list();
        }

        List<FindTagListRspVO> result = tagDOS.stream().map(sigen -> {
                    FindTagListRspVO vo = new FindTagListRspVO();
                    BeanUtils.copyProperties(sigen, vo);
                    return vo;
                }
        ).collect(Collectors.toList());

        return R.Success("查询成功",result);
    }
}
