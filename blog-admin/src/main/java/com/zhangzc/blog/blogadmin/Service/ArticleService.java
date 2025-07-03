package com.zhangzc.blog.blogadmin.Service;

import com.zhangzc.blog.blogadmin.Pojo.Vo.FindIndexArticlePageListReqVO;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.R;

import java.util.Map;


public interface ArticleService {

    PageResult findArticlePageList(FindIndexArticlePageListReqVO findIndexArticlePageListReqVO);

}
