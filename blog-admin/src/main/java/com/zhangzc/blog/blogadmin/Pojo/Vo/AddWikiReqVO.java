package com.zhangzc.blog.blogadmin.Pojo.Vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddWikiReqVO {

    private String title;


    private String summary;


    private String cover;

}
