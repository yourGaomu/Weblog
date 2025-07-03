package com.zhangzc.blog.blogcommon.pojo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String username;
    private Integer sex;
}
