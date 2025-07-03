package com.zhangzc.blog.blogadmin.Controller;

import com.zhangzc.blog.blogadmin.Service.BlogSettingsService;
import com.zhangzc.blog.blogcommon.Aop.ApiOperationLog;
import com.zhangzc.blog.blogcommon.Utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog/settings")
@RequiredArgsConstructor
public class BlogSettingsController {

    private final BlogSettingsService blogSettingsService;

    @PostMapping("/detail")
    @ApiOperationLog(description = "前台获取博客详情")
    public R findDetail() {
        return blogSettingsService.findDetail();
    }

}
