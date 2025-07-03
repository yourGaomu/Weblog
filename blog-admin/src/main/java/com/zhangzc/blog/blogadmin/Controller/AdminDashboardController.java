package com.zhangzc.blog.blogadmin.Controller;



import com.zhangzc.blog.blogadmin.Service.AdminDashboardService;
import com.zhangzc.blog.blogcommon.Aop.ApiOperationLog;
import com.zhangzc.blog.blogcommon.Aop.AroundBlogSetting;
import com.zhangzc.blog.blogcommon.Utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    @PostMapping("/statistics")
    @ApiOperationLog(description = "获取后台仪表盘基础统计信息")
    public R findDashboardStatistics() {
        return dashboardService.findDashboardStatistics();
    }

}
