package com.zhangzc.blog.blogadmin.Service;

import com.zhangzc.blog.blogadmin.Pojo.Vo.FindTagArticlePageListReqVO;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.R;

import java.util.Map;

public interface TagService {
    R findTagList();

    PageResult findTagPageList(FindTagArticlePageListReqVO findTagArticlePageListReqVO);

    R findTagList(Map<String,String> findTagArticlePageListReqVO);
}
