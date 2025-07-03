package com.zhangzc.blog.blogweb.Pojo.Vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindQQUserInfoRspVO {

    /**
     * 头像
     */
    private String avatar;
    /**
     * 昵称
     */
    @JsonProperty("nick")
    private String nickname;
    /**
     * 邮箱
     */
    @JsonProperty("email")
    private String mail;

}
