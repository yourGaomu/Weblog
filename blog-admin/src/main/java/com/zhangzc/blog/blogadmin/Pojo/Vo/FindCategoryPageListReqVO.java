package com.zhangzc.blog.blogadmin.Pojo.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhangzc.blog.blogcommon.pojo.Dto.BasePageQuery;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCategoryPageListReqVO extends BasePageQuery {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 创建的起始日期
     */

    private Date startDate;

    /**
     * 创建的结束日期
     */

    private Date endDate;

}
