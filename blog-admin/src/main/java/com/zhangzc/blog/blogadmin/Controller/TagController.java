package com.zhangzc.blog.blogadmin.Controller;

import com.zhangzc.blog.blogadmin.Pojo.Vo.FindTagArticlePageListReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindTagPageListReqVO;
import com.zhangzc.blog.blogadmin.Service.TagService;
import com.zhangzc.blog.blogcommon.Aop.ApiOperationLog;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {


    private final TagService tagService;

    @PostMapping("/list")
    @ApiOperationLog(description = "前台获取标签列表")
    public R findTagList(@RequestBody Map<String,String> findTagListReqVO) {
        return tagService.findTagList(findTagListReqVO);
    }

    @PostMapping("/article/list")
    @ApiOperationLog(description = "前台获取标签下文章列表")
    public PageResult findTagPageList(@RequestBody FindTagArticlePageListReqVO findTagArticlePageListReqVO) {
        return tagService.findTagPageList(findTagArticlePageListReqVO);
    }

}
