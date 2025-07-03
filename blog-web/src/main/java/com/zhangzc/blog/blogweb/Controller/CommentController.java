package com.zhangzc.blog.blogweb.Controller;


import com.zhangzc.blog.blogcommon.Aop.ApiOperationLog;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.exception.exceptionClasses.isCommentExamineOpenException;
import com.zhangzc.blog.blogcommon.exception.exceptionClasses.isCommentSensiException;
import com.zhangzc.blog.blogweb.Pojo.Vo.PublishCommentReqVO;
import com.zhangzc.blog.blogweb.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/qq/userInfo")
    @ApiOperationLog(description = "获取 QQ 用户信息")
    public R findQQUserInfo(@RequestBody Map<String,String> findQQUserInfoReqVO) {
        return commentService.findQQUserInfo(findQQUserInfoReqVO);
    }

    @PostMapping("/publish")
    @ApiOperationLog(description = "发布评论")
    public R publishComment(@RequestBody PublishCommentReqVO publishCommentReqVO) throws isCommentSensiException, isCommentExamineOpenException {
        return commentService.publishComment(publishCommentReqVO);
    }


    @PostMapping("/list")
    @ApiOperationLog(description = "获取页面所有评论")
    public R findPageComments(@RequestBody Map<String,String> findCommentListReqVO) {
        return commentService.findCommentList(findCommentListReqVO);
    }
}
