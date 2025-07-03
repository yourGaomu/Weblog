package com.zhangzc.blog.blogweb.Controller;


import com.zhangzc.blog.blogcommon.Aop.ApiOperationLog;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogweb.Service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;


    @PostMapping("/info")
    @ApiOperationLog(description = "前台获取统计信息")
    public R findInfo() {
        return statisticsService.findInfo();
    }

}

