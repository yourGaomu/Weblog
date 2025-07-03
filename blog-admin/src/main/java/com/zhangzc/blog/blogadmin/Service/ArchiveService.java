package com.zhangzc.blog.blogadmin.Service;

import com.zhangzc.blog.blogadmin.Pojo.Vo.FindArchiveArticlePageListReqVO;
import com.zhangzc.blog.blogcommon.Utils.PageResult;

public interface ArchiveService {
    PageResult findArchivePageList(FindArchiveArticlePageListReqVO findArchiveArticlePageListReqVO);
}
