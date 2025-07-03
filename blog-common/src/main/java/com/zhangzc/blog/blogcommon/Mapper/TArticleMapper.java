package com.zhangzc.blog.blogcommon.Mapper;

import com.zhangzc.blog.blogcommon.pojo.domain.TArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 吃饭
* @description 针对表【t_article(文章表)】的数据库操作Mapper
* @createDate 2025-06-21 15:08:41
* @Entity com.zhangzc.blog.blogcommon.pojo.domain.TArticle
*/
public interface TArticleMapper extends BaseMapper<TArticle> {

    Long CountAllArticleRead();

}




