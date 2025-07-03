package com.zhangzc.blog.blogadmin.Controller;

import com.zhangzc.blog.blogadmin.Pojo.Vo.FindCategoryArticlePageListReqVO;
import com.zhangzc.blog.blogadmin.Service.CategoryService;
import com.zhangzc.blog.blogcommon.Aop.ApiOperationLog;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/list")
    @ApiOperationLog(description = "前台获取分类列表")
    public R findCategoryList() {
        return categoryService.findCategoryList();
    }

    @PostMapping("/article/list")
    @ApiOperationLog(description = "前台获取分类下文章列表")
    public PageResult findTagPageList(@RequestBody FindCategoryArticlePageListReqVO findCategoryArticlePageListReqVO  ) {
        return categoryService.findCategoryArticlePageList(findCategoryArticlePageListReqVO);
    }
}
