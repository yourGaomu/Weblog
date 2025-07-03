package com.zhangzc.blog.blogadmin.Service;

import com.zhangzc.blog.blogadmin.Pojo.Vo.AddCategoryReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindCategoryPageListReqVO;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.R;

public interface AdminCategoryService {
    /**
     * 添加分类
     * @param addCategoryReqVO
     * @return
     */
    R addCategory(AddCategoryReqVO addCategoryReqVO);

    PageResult findCategoryList(FindCategoryPageListReqVO findCategoryPageListReqVO);

    R deleteCategoryById(Long id);

    R findCategorySelectList();
}
