package com.zhangzc.blog.blogadmin.Controller;


import com.zhangzc.blog.blogadmin.Pojo.Vo.ExamineCommentReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindCommentPageListReqVO;
import com.zhangzc.blog.blogadmin.Service.AdminCommentService;
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
@RequestMapping("/admin/comment")
@RequiredArgsConstructor
public class AdminCommentController {

    private final AdminCommentService commentService;

    @PostMapping("/list")
    @ApiOperationLog(description = "查询评论分页数据")
    public PageResult findCommentPageList(@RequestBody FindCommentPageListReqVO findCommentPageListReqVO) {
        return commentService.findCommentPageList(findCommentPageListReqVO);
    }

    @PostMapping("/delete")
    @ApiOperationLog(description = "评论删除")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public R deleteComment(@RequestBody Map<String,String> deleteCommentReqVO) {
        return commentService.deleteComment(deleteCommentReqVO);
    }

    @PostMapping("/examine")
    @ApiOperationLog(description = "评论审核")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public R examinePass(@RequestBody ExamineCommentReqVO examineCommentReqVO) {
        return commentService.examine(examineCommentReqVO);
    }


}
