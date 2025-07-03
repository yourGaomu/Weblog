package com.zhangzc.blog.blogadmin.Controller;

import com.zhangzc.blog.blogadmin.Pojo.Vo.FindArticlePageListReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.PublishArticleReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.UpdateArticleReqVO;
import com.zhangzc.blog.blogadmin.Service.AdminArticleService;
import com.zhangzc.blog.blogcommon.Aop.ApiOperationLog;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
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
@RequestMapping("/admin/article")
@RequiredArgsConstructor
public class AdminArticleController {
    private final AdminArticleService adminArticleService;

    @PostMapping("/publish")
    @ApiOperationLog(description = "文章发布")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public R publishArticle(@RequestBody PublishArticleReqVO publishArticleReqVO) {
        return adminArticleService.publishArticle(publishArticleReqVO);
    }

    @PostMapping("/delete")
    @ApiOperationLog(description = "文章删除")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public R deleteArticle(@RequestBody Map<String,String> deleteArticleReqVO) {
        return adminArticleService.deleteArticle(deleteArticleReqVO);
    }

    @PostMapping("/list")
    @ApiOperationLog(description = "查询文章分页数据")
    public PageResult findArticlePageList(@RequestBody FindArticlePageListReqVO findArticlePageListReqVO) {
        return adminArticleService.findArticlePageList(findArticlePageListReqVO);
    }

    @PostMapping("/detail")
    @ApiOperationLog(description = "查询文章详情")
    public R findArticleDetail(@RequestBody Map<String,String> findArticlePageListReqVO) {
        return adminArticleService.findArticleDetail(findArticlePageListReqVO);
    }

    @PostMapping("/update")
    @ApiOperationLog(description = "更新文章")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public R updateArticle(@RequestBody UpdateArticleReqVO updateArticleReqVO) {
        return adminArticleService.updateArticle(updateArticleReqVO);
    }

}
