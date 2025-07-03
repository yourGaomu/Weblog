package com.zhangzc.blog.blogcommon.Service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangzc.blog.blogcommon.pojo.domain.TCategory;
import com.zhangzc.blog.blogcommon.Service.TCategoryService;
import com.zhangzc.blog.blogcommon.Mapper.TCategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 吃饭
 * @description 针对表【t_category(文章分类表)】的数据库操作Service实现
 * @createDate 2025-06-13 17:04:08
 */
@Service
public class TCategoryServiceImpl extends ServiceImpl<TCategoryMapper, TCategory>
        implements TCategoryService {

    public List<TCategory> getCategoryListByPage(int page, int limit) {
        // 创建分页对象，page 是当前页，limit 是每页大小
        IPage<TCategory> pages = new Page<>(page, limit);
        // 用 lambdaQuery 构造查询条件
        IPage<TCategory> resultPage = this.lambdaQuery()
                // 这里可以加条件，比如 .eq(...), .like(...) 等
                .orderByDesc(TCategory::getCreateTime) // 按创建时间倒序
                .page(pages);  // 执行分页查询
        return resultPage.getRecords();
    }



}




