package com.zhangzc.blog.blogadmin.Controller;

import com.zhangzc.blog.blogadmin.Pojo.Vo.FindArchiveArticlePageListReqVO;
import com.zhangzc.blog.blogadmin.Service.ArchiveService;
import com.zhangzc.blog.blogcommon.Aop.ApiOperationLog;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArchiveController {

    private final ArchiveService archiveService;

    @PostMapping("/archive/list")
    @ApiOperationLog(description = "获取文章归档分页数据")
    public PageResult findArchivePageList(@RequestBody FindArchiveArticlePageListReqVO findArchiveArticlePageListReqVO) {
        return archiveService.findArchivePageList(findArchiveArticlePageListReqVO);
    }

}
