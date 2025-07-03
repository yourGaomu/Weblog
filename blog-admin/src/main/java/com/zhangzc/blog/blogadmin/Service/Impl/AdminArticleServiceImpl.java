package com.zhangzc.blog.blogadmin.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangzc.blog.blogadmin.Pojo.Vo.*;
import com.zhangzc.blog.blogadmin.Service.AdminArticleService;
import com.zhangzc.blog.blogadmin.Service.AdminTagService;
import com.zhangzc.blog.blogcommon.Service.*;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.enums.ResponseCodeEnum;
import com.zhangzc.blog.blogcommon.exception.exceptionClasses.BizException;
import com.zhangzc.blog.blogcommon.pojo.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminArticleServiceImpl implements AdminArticleService {
    private final AdminTagService adminTagService;

    private final TArticleService tArticleService;
    private final TArticleContentService tArticleContentService;
    private final TArticleTagRelService tArticleTagRelService;
    private final TArticleCategoryRelService tArticleCategoryRelService;
    private final TTagService tTagService;
    private final TCategoryService tCategoryService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public R publishArticle(PublishArticleReqVO publishArticleReqVO) {
        TArticle article = new TArticle();
        try {
            //保存文章的基本信息
            BeanUtils.copyProperties(publishArticleReqVO, article);
            article.setCreateTime(Date.valueOf(LocalDate.now()));
            article.setUpdateTime(Date.valueOf(LocalDate.now()));
            tArticleService.save(article);
            //保存文章的内容
            TArticleContent articleContent = new TArticleContent();
            articleContent.setArticleId(article.getId());
            articleContent.setContent(publishArticleReqVO.getContent());
            tArticleContentService.save(articleContent);
            //标签处理
            AddTags(publishArticleReqVO, article.getId());
            //保存文章的分类
            //保存文章的分类是否存在
            TCategory result = tCategoryService.lambdaQuery()
                    .eq(TCategory::getId, publishArticleReqVO.getCategoryId()).one();
            if (Objects.isNull(result)) {
                throw new BizException(ResponseCodeEnum.TARTICLECATEGORYREL_ID_NOTEXIT);
            }
            TArticleCategoryRel articleCategoryRel = new TArticleCategoryRel();
            articleCategoryRel.setArticleId(article.getId());
            articleCategoryRel.setCategoryId(publishArticleReqVO.getCategoryId());
            tArticleCategoryRelService.save(articleCategoryRel);
        } catch (Exception e) {
            throw new BizException("500","保存失败");
        }
        return R.Success("文章" + article.getTitle() + "保存成功");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public R deleteArticle(Map<String, String> deleteArticleReqVO) {
        Long articleId = Long.valueOf(deleteArticleReqVO.get("id"));
        try {
            //删除文章
            tArticleService.removeById(articleId);
            //删除文章的内容
            LambdaQueryWrapper<TArticleContent> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TArticleContent::getArticleId, articleId);
            tArticleContentService.remove(queryWrapper);
            //删除文章的分类
            LambdaQueryWrapper<TArticleCategoryRel> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(TArticleCategoryRel::getArticleId, articleId);
            tArticleCategoryRelService.remove(queryWrapper1);
            //删除文章的标签
            LambdaQueryWrapper<TArticleTagRel> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(TArticleTagRel::getArticleId, articleId);
            tArticleTagRelService.remove(queryWrapper2);
        } catch (Exception e) {
            throw new BizException("500","更新失败");
        }
        return R.Success("删除成功");
    }

    @Override
    public PageResult findArticlePageList(FindArticlePageListReqVO findArticlePageListReqVO) {
        Page<TArticle> pageListRspVO = new Page<>();
        LambdaQueryWrapper<TArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(findArticlePageListReqVO.getTitle() != null, TArticle::getTitle, findArticlePageListReqVO.getTitle())
                .gt(findArticlePageListReqVO.getStartDate() != null, TArticle::getCreateTime, findArticlePageListReqVO.getStartDate())
                .lt(findArticlePageListReqVO.getEndDate() != null, TArticle::getCreateTime, findArticlePageListReqVO.getEndDate())
                .orderByDesc(TArticle::getCreateTime);

        Page<TArticle> page = tArticleService.page(pageListRspVO, queryWrapper);
        List<FindArticlePageListRspVO> collect = page.getRecords().stream().map(record -> {
            FindArticlePageListRspVO findArticlePageListRspVO = new FindArticlePageListRspVO();
            BeanUtils.copyProperties(record, findArticlePageListRspVO);
            return findArticlePageListRspVO;
        }).collect(Collectors.toList());


        return PageResult.Success(page, collect);
    }

    @Override
    public R findArticleDetail(Map<String, String> findArticlePageListReqVO) {
        Long ArticleId = Long.valueOf(findArticlePageListReqVO.get("id"));
        FindArticleDetailRspVO result = new FindArticleDetailRspVO();
        TArticle byId = tArticleService.getById(ArticleId);
        if (Objects.isNull(byId)) {
            return R.Faile("文章不存在");
        }
        BeanUtils.copyProperties(byId, result);
        TArticleContent one = tArticleContentService.lambdaQuery()
                .eq(TArticleContent::getArticleId, ArticleId).one();
        result.setContent(one.getContent());
        TArticleCategoryRel one1 = tArticleCategoryRelService.lambdaQuery()
                .eq(TArticleCategoryRel::getArticleId, ArticleId).one();
        result.setCategoryId(one1.getCategoryId());
        List<Long> collect = tArticleTagRelService.lambdaQuery()
                .eq(TArticleTagRel::getArticleId, ArticleId)
                .list().stream()
                .map(TArticleTagRel::getTagId).collect(Collectors.toList());
        result.setTagIds(collect);
        return R.Success("查找成功", result);
    }

    @Override
    // 删除Redis存储的文章
    @CacheEvict(cacheNames = "Article", key = "#updateArticleReqVO.id",condition = "result.status.equals(200)")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public R updateArticle(UpdateArticleReqVO updateArticleReqVO) {
        TArticle article = new TArticle();
        try {
            BeanUtils.copyProperties(updateArticleReqVO, article);
            article.setUpdateTime(Date.valueOf(LocalDate.now()));
            tArticleService.updateById(article);
            //更新文章的内容
            LambdaUpdateWrapper<TArticleContent> queryWrapper = new LambdaUpdateWrapper<>();
            queryWrapper.eq(TArticleContent::getArticleId, article.getId())
                    .set(TArticleContent::getContent, updateArticleReqVO.getContent());
            tArticleContentService.update(queryWrapper);
            LambdaUpdateWrapper<TArticleTagRel> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(TArticleTagRel::getArticleId, article.getId());
            //先把标签删除了
            tArticleTagRelService.remove(updateWrapper);
            PublishArticleReqVO publishArticleReqVO = new PublishArticleReqVO();
            //重新添加标签
            BeanUtils.copyProperties(updateArticleReqVO, publishArticleReqVO);
            AddTags(publishArticleReqVO, article.getId());
            //更新分类
            tArticleCategoryRelService.lambdaUpdate().eq(TArticleCategoryRel::getArticleId, article.getId())
                    .set(TArticleCategoryRel::getCategoryId, updateArticleReqVO.getCategoryId()).update();
        }catch (Exception e) {
            throw new BizException("500","更新失败");
        }
        return R.Success("更新成功");
    }

    private void AddTags(PublishArticleReqVO publishArticleReqVO, Long articleId) {
        //保存文章的标签
        //添加提交标签中的不存在于表中的标签
        adminTagService.addTags(AddTagReqVO.builder()
                .tags(publishArticleReqVO.getTags())
                .build());
        //获取当前需要添加标签的ID
        Map<String, Long> tagMap = tTagService.lambdaQuery()
                .in(TTag::getName, publishArticleReqVO.getTags())
                .list().stream()
                .collect(Collectors.toMap(
                        TTag::getName,   // keyMapper: 标签名作为键
                        TTag::getId,     // valueMapper: 标签ID作为值
                        (existing, replacement) -> existing  // 处理键冲突的合并函数
                ));

        //获取当前的有效标签
        List<TArticleTagRel> tags = publishArticleReqVO.getTags().stream()
                .map(sign -> {
                    TArticleTagRel rel = new TArticleTagRel();
                    rel.setArticleId(articleId);
                    rel.setTagId(tagMap.get(sign));
                    return rel;
                })
                .filter(obj -> true) // 过滤掉转换失败的null值
                .collect(Collectors.toList());
        tArticleTagRelService.saveBatch(tags);
    }
}
