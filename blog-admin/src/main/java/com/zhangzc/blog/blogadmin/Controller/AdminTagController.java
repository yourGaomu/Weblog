package com.zhangzc.blog.blogadmin.Controller;

import com.zhangzc.blog.blogadmin.Service.AdminTagService;
import com.zhangzc.blog.blogadmin.Service.TagService;
import com.zhangzc.blog.blogcommon.Aop.ApiOperationLog;
import com.zhangzc.blog.blogcommon.Utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/tag")
public class AdminTagController {
    private final AdminTagService adminTagService;
    private final TagService tagService;

    @PostMapping("/list")
    @ApiOperationLog(description = "前台获取标签列表")
    public R findTagList() {
        return tagService.findTagList();
    }

    @PostMapping("/select/list")
    @ApiOperationLog(description = "查询标签 Select 列表数据")
    public R findTagSelectList() {
        return adminTagService.findTagSelectList();
    }

    @DeleteMapping()
    @ApiOperationLog(description = "删除标签")
    public R deleteTag() {
        return null;
    }

}
