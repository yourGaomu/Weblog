package com.zhangzc.blog.blogadmin.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangzc.blog.blogadmin.Pojo.Vo.*;
import com.zhangzc.blog.blogadmin.Service.AdminTagService;
import com.zhangzc.blog.blogcommon.Service.TTagService;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.pojo.domain.TTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminTagServiceImpl implements AdminTagService {

    private final TTagService tTagService;

    @Override
    public R addTags(AddTagReqVO addTagReqVO) {
        List<String> tags = addTagReqVO.getTags();
        // 获取已存在的标签名称列表
        List<String> existingTags = getAllTags()
                .stream()
                .map(TTag::getName)
                .collect(Collectors.toList());

        // 过滤出不存在的标签，生成待保存的标签实体
        List<TTag> newTags = tags.stream()
                .filter(tagName -> !existingTags.contains(tagName)) // 关键修改：使用!contains
                .map(tagName -> {
                    TTag tag = new TTag();
                    tag.setName(tagName);
                    tag.setCreateTime(Date.valueOf(LocalDate.now()));
                    tag.setUpdateTime(Date.valueOf(LocalDate.now()));
                    return tag;
                })
                .collect(Collectors.toList());

        // 批量保存新标签
        if (!newTags.isEmpty()) {
            tTagService.saveBatch(newTags);
        }

        return R.Success("添加标签成功");
    }

    @Override
    public R deleteTag(DeleteTagReqVO deleteTagReqVO) {
        tTagService.removeById(deleteTagReqVO.getId());
        return R.Success("删除成功");
    }

    @Override
    public R searchTags(SearchTagsReqVO searchTagsReqVO) {
        String key = searchTagsReqVO.getKey();
        List<TTag> list = tTagService.lambdaQuery()
                .like(TTag::getName, key).list();
        return R.Success("查找成功", list);
    }

    @Override
    public PageResult findTagPageList(FindTagPageListReqVO findTagPageListReqVO) {
        // 分页对象(查询第几页、每页多少数据)
        Page<TTag> page = new Page<>(findTagPageListReqVO.getCurrent(), findTagPageListReqVO.getSize());
        //构建查询条件
        LambdaQueryWrapper<TTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .gt(findTagPageListReqVO.getStartDate() != null, TTag::getCreateTime, findTagPageListReqVO.getStartDate())
                .lt(findTagPageListReqVO.getEndDate() != null, TTag::getCreateTime, findTagPageListReqVO.getEndDate())
                .like(findTagPageListReqVO.getName() != null, TTag::getName, findTagPageListReqVO.getName())
                .orderByDesc(TTag::getCreateTime);

        Page<TTag> page1 = tTagService.page(page, lambdaQueryWrapper);

        return PageResult.Success(page1, page1.getRecords());
    }

    @Override
    public R findTagSelectList() {
        List<SelectTagRspVO> result = tTagService.lambdaQuery(null).list().stream().map(record -> {
            SelectTagRspVO selectTagRspVO = new SelectTagRspVO();
            selectTagRspVO.setLabel(record.getName());
            selectTagRspVO.setValue(record.getId());
            return selectTagRspVO;
        }).collect(Collectors.toList());
        return R.Success("查询成功", result);
    }

    private List<TTag> getAllTags() {
        return tTagService.list();
    }


}
