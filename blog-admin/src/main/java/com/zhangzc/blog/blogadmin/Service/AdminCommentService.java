package com.zhangzc.blog.blogadmin.Service;

import com.zhangzc.blog.blogadmin.Pojo.Vo.ExamineCommentReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindCommentPageListReqVO;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.R;

import java.util.Map;

public interface AdminCommentService {
    PageResult findCommentPageList(FindCommentPageListReqVO findCommentPageListReqVO);

    R deleteComment(Map<String, String> deleteCommentReqVO);

    R examine(ExamineCommentReqVO examineCommentReqVO);
}
