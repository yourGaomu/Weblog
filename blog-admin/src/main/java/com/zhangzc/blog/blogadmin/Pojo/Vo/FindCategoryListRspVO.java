package com.zhangzc.blog.blogadmin.Pojo.Vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCategoryListRspVO {
    private Long id;
    private String name;
}
