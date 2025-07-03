package com.zhangzc.blog.blogcommon.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangzc.blog.blogcommon.pojo.domain.TComment;
import com.zhangzc.blog.blogcommon.Service.TCommentService;
import com.zhangzc.blog.blogcommon.Mapper.TCommentMapper;
import org.springframework.stereotype.Service;

/**
* @author 吃饭
* @description 针对表【t_comment(评论表)】的数据库操作Service实现
* @createDate 2025-06-24 16:53:20
*/
@Service
public class TCommentServiceImpl extends ServiceImpl<TCommentMapper, TComment>
    implements TCommentService{

}




