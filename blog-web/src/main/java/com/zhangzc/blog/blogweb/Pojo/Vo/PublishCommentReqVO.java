package com.zhangzc.blog.blogweb.Pojo.Vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublishCommentReqVO {

    /**
     * 头像
     */
    private String avatar;

    private String nickname;

    private String mail;

    /**
     * 网址
     */
    private String website;

    private String routerUrl;

    private String content;

    /**
     * 回复的评论 ID
     */
    private Long replyCommentId;

    /**
     * 父评论 ID
     */
    private Long parentCommentId;

}

