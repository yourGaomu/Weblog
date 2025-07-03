package com.zhangzc.blog.blogadmin.Pojo.Vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCommentPageListRspVO {
    
    private Long id;

    private String routerUrl;

    private String avatar;

    private String nickname;

    private String mail;

    private String website;

    private Date createTime;

    private String content;

    private Integer status;

    private String reason;
}

