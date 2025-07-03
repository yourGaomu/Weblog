package com.zhangzc.blog.blogweb.Service;

import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogweb.Pojo.Vo.FindArticleDetailReqVO;

import java.util.Map;

public interface ArticleServiceTwo {
    R findArticleDetail(Map<String,String> findArticleDetailReqVO);
}
