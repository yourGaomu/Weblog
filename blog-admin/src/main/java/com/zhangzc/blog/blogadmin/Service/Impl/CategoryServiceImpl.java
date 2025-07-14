package com.zhangzc.blog.blogadmin.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindCategoryArticlePageListReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindCategoryArticlePageListRspVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindCategoryListRspVO;
import com.zhangzc.blog.blogadmin.Service.CategoryService;
import com.zhangzc.blog.blogcommon.Service.TArticleCategoryRelService;
import com.zhangzc.blog.blogcommon.Service.TArticleService;
import com.zhangzc.blog.blogcommon.Service.TCategoryService;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.pojo.domain.TArticle;
import com.zhangzc.blog.blogcommon.pojo.domain.TArticleCategoryRel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final TCategoryService tCategoryService;
    private final TArticleCategoryRelService tArticleCategoryRelService;
    private final TArticleService tArticleService;

    @Override
    public R findCategoryList() {
        List<FindCategoryListRspVO> collect = tCategoryService.list(null)
                .stream()
                .map(record -> {
                    FindCategoryListRspVO vo = new FindCategoryListRspVO();
                    BeanUtils.copyProperties(record, vo);
                    return vo;
                }).collect(Collectors.toList());
        return R.Success("查找成功",collect);
    }

    @Override
    public PageResult findCategoryArticlePageList(FindCategoryArticlePageListReqVO findCategoryArticlePageListReqVO) {
        //构建查询的page
        IPage<TArticleCategoryRel> page = new Page<>(findCategoryArticlePageListReqVO.getCurrent(),
                findCategoryArticlePageListReqVO.getSize());
        //构建查询的条件
        LambdaQueryWrapper<TArticleCategoryRel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TArticleCategoryRel::getCategoryId, findCategoryArticlePageListReqVO.getId());
        IPage<TArticleCategoryRel> page1 = tArticleCategoryRelService.page(page, wrapper);
        //获得结果
        List<TArticleCategoryRel> records = page1.getRecords();
        //获取该分类下的文章ID
        List<Long> collect = records.stream().map(TArticleCategoryRel::getArticleId)
                .collect(Collectors.toList());
        List<TArticle> list = tArticleService.lambdaQuery().in(TArticle::getId, collect).list();
        List<FindCategoryArticlePageListRspVO> result = list.stream().map(record -> {
            FindCategoryArticlePageListRspVO vo = new FindCategoryArticlePageListRspVO();
            BeanUtils.copyProperties(record, vo);
            vo.setCreateDate(record.getCreateTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            return vo;
        }).collect(Collectors.toList());

        return PageResult.Success(page1,result);
    }
}
