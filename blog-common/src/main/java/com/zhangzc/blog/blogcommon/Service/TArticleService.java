package com.zhangzc.blog.blogcommon.Service;

import com.zhangzc.blog.blogcommon.pojo.domain.TArticle;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 吃饭
* @description 针对表【t_article(文章表)】的数据库操作Service
* @createDate 2025-06-21 15:08:41
*/
public interface TArticleService extends IService<TArticle> {

    public Long CountAllArticleRead();

}
