package com.zhangzc.blog.blogweb.Service.Impl;

import com.zhangzc.blog.blogadmin.Pojo.Vo.FindTagListRspVO;
import com.zhangzc.blog.blogcommon.Service.*;
import com.zhangzc.blog.blogcommon.Utils.MQUtil;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.Utils.RedisUtil;
import com.zhangzc.blog.blogcommon.pojo.domain.*;
import com.zhangzc.blog.blogweb.Pojo.Vo.FindArticleDetailReqVO;
import com.zhangzc.blog.blogweb.Pojo.Vo.FindArticleDetailRspVO;
import com.zhangzc.blog.blogweb.Pojo.Vo.FindPreNextArticleRspVO;
import com.zhangzc.blog.blogweb.Service.ArticleServiceTwo;
import com.zhangzc.blog.blogweb.Utils.MarkdownHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceTwoImpl implements ArticleServiceTwo {
    private final TArticleService articleService;
    private final TArticleContentService articleContentService;
    private final TArticleTagRelService articleTagRelService;
    private final TArticleCategoryRelService articleCategoryRelService;
    private final TCategoryService categoryService;
    private final TTagService tagService;

    @Override
    @Cacheable(cacheNames = "Article",key = "#now['articleId']")//存储文章信息，减少压力
    public R findArticleDetail(Map<String,String> now) {
        Long articleId = Long.valueOf(now.get("articleId"));
        FindArticleDetailRspVO findArticleDetailRspVO = new FindArticleDetailRspVO();
        //查找当前的前后文章
        FindPreNextArticleRspVO preVo = findPre(articleId);
        findArticleDetailRspVO.setPreArticle(preVo);
        FindPreNextArticleRspVO aftVo = finLate(articleId);
        findArticleDetailRspVO.setNextArticle(aftVo);
        //查找当前文章的属性
        TArticle article = articleService.getById(articleId);
        BeanUtils.copyProperties(article, findArticleDetailRspVO);
        findArticleDetailRspVO.setReadNum(Long.valueOf(article.getReadNum()));
        //查找当前文章的内容
        TArticleContent one = articleContentService.lambdaQuery().eq(TArticleContent::getArticleId, articleId).one();
        String content = MarkdownHelper.convertMarkdown2Html(one.getContent());
        findArticleDetailRspVO.setContent(content);
        //查找当前文章的分类ID和名字
        TArticleCategoryRel one1 = articleCategoryRelService.lambdaQuery().eq(TArticleCategoryRel::getArticleId, articleId)
                .one();
        findArticleDetailRspVO.setCategoryId(one1.getCategoryId());
        TCategory byId = categoryService.getById(one1.getCategoryId());
        findArticleDetailRspVO.setCategoryName(byId.getName());
        //查找当前文章的标签集合
        List<TArticleTagRel> list = articleTagRelService.lambdaQuery().eq(TArticleTagRel::getArticleId, articleId)
                .list();
        List<Long> collect = list.stream().map(TArticleTagRel::getTagId).collect(Collectors.toList());
        List<FindTagListRspVO> collect1 = tagService.lambdaQuery().in(TTag::getId, collect).list()
                .stream()
                .map(Tag -> {
                    FindTagListRspVO vo = new FindTagListRspVO();
                    vo.setId(Tag.getId());
                    vo.setName(Tag.getName());
                    return vo;
                }).collect(Collectors.toList());
        findArticleDetailRspVO.setTags(collect1);
        return R.Success("查找成功", findArticleDetailRspVO);
    }

    private FindPreNextArticleRspVO finLate(Long articleId) {
        TArticle one = articleService.lambdaQuery()
                .orderByAsc(TArticle::getId)
                .gt(TArticle::getId, articleId)
                .last("limit 1").one();
        if (one == null) {
            return null;
        }
        return FindPreNextArticleRspVO.builder()
                .articleId(one.getId())
                .articleTitle(one.getTitle())
                .build();
    }

    private FindPreNextArticleRspVO findPre(Long articleId) {
        TArticle one = articleService.lambdaQuery()
                .orderByDesc(TArticle::getId)
                .lt(TArticle::getId, articleId)
                .last("limit 1").one();

        if (one == null) {
            return null;
        }

        return FindPreNextArticleRspVO.builder()
                .articleId(one.getId())
                .articleTitle(one.getTitle())
                .build();
    }
}
