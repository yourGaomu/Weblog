package com.zhangzc.blog.blogadmin.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindCategoryListRspVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindIndexArticlePageListReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindIndexArticlePageListRspVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindTagListRspVO;
import com.zhangzc.blog.blogadmin.Service.ArticleService;
import com.zhangzc.blog.blogcommon.Service.*;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.pojo.domain.TArticle;
import com.zhangzc.blog.blogcommon.pojo.domain.TArticleCategoryRel;
import com.zhangzc.blog.blogcommon.pojo.domain.TArticleTagRel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ArticleServiceImpl implements ArticleService {

    private final TArticleCategoryRelService tArticleCategoryRelService;
    private final TArticleService tArticleService;
    private final TArticleTagRelService tArticleTagRelService;
    private final TCategoryService tCategoryService;
    private final TTagService tTagService;

    @Override
    public PageResult findArticlePageList(FindIndexArticlePageListReqVO findIndexArticlePageListReqVO) {
        //设置当前的页码和大小
        Page<TArticle> page = new Page<>();
        page.setCurrent(findIndexArticlePageListReqVO.getCurrent());
        page.setSize(findIndexArticlePageListReqVO.getSize());
        //根据当前的查询有多少文章
        LambdaQueryWrapper<TArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TArticle::getCreateTime);
        Page<TArticle> Article = tArticleService.page(page, wrapper);
        List<TArticle> records = Article.getRecords();
        //查询这些文章有哪些分类
        List<FindIndexArticlePageListRspVO> result = records.stream().map(record -> {
            FindIndexArticlePageListRspVO findIndexArticlePageListRspVO = new FindIndexArticlePageListRspVO();
            //将部分属性复制
            BeanUtils.copyProperties(record, findIndexArticlePageListRspVO);
            //获取当前文章的分类
            SetCategories(findIndexArticlePageListRspVO, record.getId());
            //获取当前文章的标签
            SetTagS(findIndexArticlePageListRspVO, record.getId());
            return findIndexArticlePageListRspVO;
        }).collect(Collectors.toList());
        return PageResult.Success(Article, result);
    }



    private void SetTagS(FindIndexArticlePageListRspVO findIndexArticlePageListRspVO, Long articleId) {
        List<Long> collect = tArticleTagRelService.lambdaQuery()
                .eq(TArticleTagRel::getArticleId, articleId)
                .list().stream()
                .map(record -> {
                    Long tagId = record.getTagId();
                    return tagId;
                }).collect(Collectors.toList());

        List<FindTagListRspVO> result = tTagService.listByIds(collect).stream().map(record -> {
            FindTagListRspVO vo = new FindTagListRspVO();
            BeanUtils.copyProperties(record, vo);
            return vo;
        }).collect(Collectors.toList());
        findIndexArticlePageListRspVO.setTags(result);
    }

    private void SetCategories(FindIndexArticlePageListRspVO findIndexArticlePageListRspVO, Long articleId) {
        List<Long> categories = tArticleCategoryRelService.lambdaQuery()
                .eq(TArticleCategoryRel::getArticleId, articleId).list().stream()
                .map(record -> {
                    Long categoryId = record.getCategoryId();
                    return categoryId;
                }).collect(Collectors.toList());
        System.out.println(categories);
        List<FindCategoryListRspVO> result = tCategoryService.listByIds(categories).stream()
                .map(record -> {
                    FindCategoryListRspVO findCategoryListRspVO = new FindCategoryListRspVO();
                    BeanUtils.copyProperties(record, findCategoryListRspVO);
                    return findCategoryListRspVO;
                }).collect(Collectors.toList());

        findIndexArticlePageListRspVO.setCategory(result.get(0));
    }
}
