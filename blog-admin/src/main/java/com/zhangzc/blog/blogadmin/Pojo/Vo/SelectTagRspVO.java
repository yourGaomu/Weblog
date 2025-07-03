package com.zhangzc.blog.blogadmin.Pojo.Vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectTagRspVO {
    public Long value;
    public String label;
}
