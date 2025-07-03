package com.zhangzc.blog.blogadmin.Controller;

import com.zhangzc.blog.blogadmin.Pojo.Vo.UpdateBlogSettingsReqVO;
import com.zhangzc.blog.blogadmin.Service.AdminBlogSettingsService;
import com.zhangzc.blog.blogcommon.Aop.AfterBlogSetting;
import com.zhangzc.blog.blogcommon.Aop.ApiOperationLog;
import com.zhangzc.blog.blogcommon.Utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/blog/settings")
@RequiredArgsConstructor
public class AdminBlogSettingsServiceController {
    private final AdminBlogSettingsService adminBlogSettingsService;

    @PostMapping("/update")
    @ApiOperationLog(description = "博客基础信息修改")
    @AfterBlogSetting(isCommentSensiWordOpen = "#updateBlogSettingsReqVO.isCommentSensiWordOpen"
            , isCommentExamineOpen = "#updateBlogSettingsReqVO.isCommentExamineOpen")
    public R updateBlogSettings(@RequestBody UpdateBlogSettingsReqVO updateBlogSettingsReqVO) {
        return adminBlogSettingsService.updateBlogSettings(updateBlogSettingsReqVO);
    }

    @PostMapping("/detail")
    @ApiOperationLog(description = "获取博客设置详情")
    public R findDetail() {
        return adminBlogSettingsService.findDetail();
    }

}
