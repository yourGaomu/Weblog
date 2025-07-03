package com.zhangzc.blog.blogadmin.Service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangzc.blog.blogadmin.Pojo.Vo.AddCategoryReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindCategoryPageListReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.SelectRspVO;
import com.zhangzc.blog.blogadmin.Service.AdminCategoryService;
import com.zhangzc.blog.blogcommon.Service.TCategoryService;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.enums.ResponseCodeEnum;
import com.zhangzc.blog.blogcommon.exception.exceptionClasses.BizException;
import com.zhangzc.blog.blogcommon.pojo.domain.TCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor

public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final TCategoryService tCategoryService;

    /**
     * 添加分类
     *
     * @param addCategoryReqVO
     * @return
     */
    @Override
    public R addCategory(AddCategoryReqVO addCategoryReqVO) {
        String categoryName = addCategoryReqVO.getName();

        // 先判断该分类是否已经存在
        TCategory categoryDO = tCategoryService.lambdaQuery().eq(TCategory::getName, categoryName).one();

        if (Objects.nonNull(categoryDO)) {
            log.warn("分类名称： {}, 此分类已存在", categoryName);
            throw new BizException(ResponseCodeEnum.CATEGORY_NAME_IS_EXISTED);
        }

        // 构建 DO 类
        TCategory insertCategoryDO = TCategory.builder()
                .name(addCategoryReqVO.getName().trim())
                .build();

        // 执行 insert
        boolean result = tCategoryService.save(insertCategoryDO);
        if (result)
            return R.Success("添加成功");
        return R.Faile("添加失败");
    }

    @Override
    public PageResult findCategoryList(FindCategoryPageListReqVO findCategoryPageListReqVO) {
        // 分页对象(查询第几页、每页多少数据)
        Page<TCategory> page = new Page<>(findCategoryPageListReqVO.getCurrent(), findCategoryPageListReqVO.getSize());

        LambdaQueryWrapper<TCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(TCategory::getName, findCategoryPageListReqVO.getName())
                .gt(findCategoryPageListReqVO.getStartDate() != null, TCategory::getCreateTime, findCategoryPageListReqVO.getStartDate())
                .lt(findCategoryPageListReqVO.getEndDate() != null, TCategory::getCreateTime, findCategoryPageListReqVO.getEndDate())
                .orderByDesc(TCategory::getCreateTime); // 按创建时间倒叙

        Page<TCategory> pages = tCategoryService.page(page, lambdaQueryWrapper);
        List<TCategory> records = pages.getRecords();

        List<FindCategoryPageListReqVO> collect = records.stream().map(record -> {
            FindCategoryPageListReqVO findCategoryPageListReqVO1 = new FindCategoryPageListReqVO();
            findCategoryPageListReqVO1.setName(record.getName());
            findCategoryPageListReqVO1.setStartDate(record.getCreateTime());
            if (record.getIsDeleted() == 1) {
                findCategoryPageListReqVO1.setEndDate(record.getUpdateTime());
            }
            return findCategoryPageListReqVO1;
        }).collect(Collectors.toList());
        return PageResult.Success(pages, collect);

    }

    @Override
    public R deleteCategoryById(Long id) {
        boolean update = tCategoryService.lambdaUpdate()
                .eq(TCategory::getId, id)
                .set(TCategory::getIsDeleted, 1)
                .update();
        return R.Boolen(update,"操作成功");
    }

    @Override
    public R findCategorySelectList() {
        List<TCategory> list = tCategoryService.lambdaQuery(null).list();
        List<SelectRspVO> collect = list.stream().map(record -> {
            SelectRspVO selectRspVO = new SelectRspVO();
            selectRspVO.setLabel(record.getName());
            selectRspVO.setValue(record.getId());
            return selectRspVO;
        }).collect(Collectors.toList());
        return R.Success("查询成功",collect);
    }


}

