package com.zhangzc.blog.blogsearch.Pojo.Vo;


import com.zhangzc.blog.blogcommon.pojo.Dto.BasePageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SearchArticlePageListReqVO extends BasePageQuery {
    /**
     * 查询关键词
     */
    private String word;
}

