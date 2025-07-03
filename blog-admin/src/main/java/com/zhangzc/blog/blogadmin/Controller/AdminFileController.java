package com.zhangzc.blog.blogadmin.Controller;

import com.zhangzc.blog.blogadmin.Service.AdminFileService;
import com.zhangzc.blog.blogcommon.Aop.ApiOperationLog;
import com.zhangzc.blog.blogcommon.Utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminFileController {
    private final AdminFileService adminFileService;


    @PostMapping("/file/upload")
    @ApiOperationLog(description = "文件上传")
    public R uploadFile(@RequestParam MultipartFile file) throws Exception {
        return adminFileService.uploadFile(file);
    }
}
