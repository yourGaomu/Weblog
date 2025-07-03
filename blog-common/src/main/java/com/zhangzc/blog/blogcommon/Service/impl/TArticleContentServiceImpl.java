package com.zhangzc.blog.blogcommon.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangzc.blog.blogcommon.pojo.domain.TArticleContent;
import com.zhangzc.blog.blogcommon.Service.TArticleContentService;
import com.zhangzc.blog.blogcommon.Mapper.TArticleContentMapper;
import org.springframework.stereotype.Service;

/**
* @author 吃饭
* @description 针对表【t_article_content(文章内容表)】的数据库操作Service实现
* @createDate 2025-06-21 15:08:41
*/
@Service
public class TArticleContentServiceImpl extends ServiceImpl<TArticleContentMapper, TArticleContent>
    implements TArticleContentService{

}




