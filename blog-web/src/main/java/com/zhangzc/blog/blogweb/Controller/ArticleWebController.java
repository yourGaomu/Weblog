package com.zhangzc.blog.blogweb.Controller;

import com.zhangzc.blog.blogcommon.Aop.AddPointByArtID;
import com.zhangzc.blog.blogcommon.Aop.ApiOperationLog;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.Utils.RedisUtil;
import com.zhangzc.blog.blogweb.Service.ArticleServiceTwo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleWebController {

	private final ArticleServiceTwo articleService;

    @PostMapping("/detail")
    @AddPointByArtID("#findArticleDetailReqVO['articleId']")
    public R findArticleDetail(@RequestBody Map<String,String> findArticleDetailReqVO) {
        return articleService.findArticleDetail(findArticleDetailReqVO);
    }

}
