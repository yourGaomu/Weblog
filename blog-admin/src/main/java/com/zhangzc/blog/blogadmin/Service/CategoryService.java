package com.zhangzc.blog.blogadmin.Service;

import com.zhangzc.blog.blogadmin.Pojo.Vo.FindCategoryArticlePageListReqVO;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.R;

import java.util.Map;

public interface CategoryService {
    R findCategoryList();

    PageResult findCategoryArticlePageList(FindCategoryArticlePageListReqVO findCategoryArticlePageListReqVO);

}
