package com.zhangzc.blog.blogadmin.Controller;

import com.zhangzc.blog.blogadmin.Pojo.Vo.AddWikiReqVO;
import com.zhangzc.blog.blogadmin.Service.AdminWikiService;
import com.zhangzc.blog.blogcommon.Aop.ApiOperationLog;
import com.zhangzc.blog.blogcommon.Utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/wiki")
public class AdminWikiController {


    private final AdminWikiService wikiService;

    @PostMapping("/add")
    @ApiOperationLog(description = "新增知识库")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public R addWiki(@RequestBody AddWikiReqVO addWikiReqVO) {
        return wikiService.addWiki(addWikiReqVO);
    }

    @PostMapping("/delete")
    @ApiOperationLog(description = "知识库删除")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public R deleteWiki(@RequestBody Map<String,String> deleteWikiReqVO) {
        return wikiService.deleteWiki(deleteWikiReqVO);
    }

}
