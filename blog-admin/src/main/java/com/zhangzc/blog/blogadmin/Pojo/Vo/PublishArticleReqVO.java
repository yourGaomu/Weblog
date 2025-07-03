package com.zhangzc.blog.blogadmin.Pojo.Vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublishArticleReqVO {

    private String title;

    private String content;

    private String cover;

    private String summary;

    private Long categoryId;

    private List<String> tags;
}
