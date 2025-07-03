package com.zhangzc.blog.blogadmin.Service;

import com.zhangzc.blog.blogadmin.Pojo.Vo.FindArticlePageListReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.PublishArticleReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.UpdateArticleReqVO;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.R;

import java.util.Map;

public interface AdminArticleService {
    R publishArticle(PublishArticleReqVO publishArticleReqVO);

    R deleteArticle(Map<String, String> deleteArticleReqVO);

    PageResult findArticlePageList(FindArticlePageListReqVO findArticlePageListReqVO);

    R findArticleDetail(Map<String, String> findArticlePageListReqVO);

    R updateArticle(UpdateArticleReqVO updateArticleReqVO);
}
