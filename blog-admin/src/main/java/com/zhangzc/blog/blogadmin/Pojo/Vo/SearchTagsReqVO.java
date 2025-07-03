package com.zhangzc.blog.blogadmin.Pojo.Vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author: 犬小哈
 * @url: www.quanxiaoha.com
 * @date: 2023-09-15 14:07
 * @description: 标签模糊查询
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchTagsReqVO {

    private String key;

}
