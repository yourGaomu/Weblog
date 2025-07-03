package com.zhangzc.blog.blogadmin.Pojo.Vo;

import com.zhangzc.blog.blogcommon.pojo.Dto.BasePageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindTagArticlePageListReqVO extends BasePageQuery {

    /**
     * 标签 ID
     */
    private Long id;

}
