package com.zhangzc.blog.blogweb.Service;

import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.exception.exceptionClasses.isCommentExamineOpenException;
import com.zhangzc.blog.blogcommon.exception.exceptionClasses.isCommentSensiException;
import com.zhangzc.blog.blogweb.Pojo.Vo.PublishCommentReqVO;

import java.util.Map;

public interface CommentService {
    R findQQUserInfo(Map<String, String> findQQUserInfoReqVO);

    R publishComment(PublishCommentReqVO publishCommentReqVO) throws isCommentExamineOpenException, isCommentSensiException;

    R findCommentList(Map<String, String> findCommentListReqVO);
}
