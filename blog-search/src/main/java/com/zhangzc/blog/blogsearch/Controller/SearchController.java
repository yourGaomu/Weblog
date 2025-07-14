package com.zhangzc.blog.blogsearch.Controller;


import com.zhangzc.blog.blogcommon.Aop.ApiOperationLog;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogsearch.Pojo.Vo.SearchArticlePageListReqVO;
import com.zhangzc.blog.blogsearch.Service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {


    private final SearchService searchService;

    @PostMapping("/article/search")
    @ApiOperationLog(description = "文章搜索")
    public PageResult searchArticlePageList(@RequestBody SearchArticlePageListReqVO searchArticlePageListReqVO) {
        return searchService.searchArticlePageList(searchArticlePageListReqVO);
    }

}
