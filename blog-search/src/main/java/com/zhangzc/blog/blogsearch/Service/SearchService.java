package com.zhangzc.blog.blogsearch.Service;

import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogsearch.Pojo.Vo.SearchArticlePageListReqVO;

public interface SearchService {
    PageResult searchArticlePageList(SearchArticlePageListReqVO searchArticlePageListReqVO);
}
