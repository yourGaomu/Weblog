package com.zhangzc.blog.blogcommon.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangzc.blog.blogcommon.pojo.domain.TArticle;
import com.zhangzc.blog.blogcommon.Service.TArticleService;
import com.zhangzc.blog.blogcommon.Mapper.TArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 吃饭
 * @description 针对表【t_article(文章表)】的数据库操作Service实现
 * @createDate 2025-06-21 15:08:41
 */
@Service
@RequiredArgsConstructor
public class TArticleServiceImpl extends ServiceImpl<TArticleMapper, TArticle>
        implements TArticleService {

    private final TArticleMapper articleMapper;

    public Long CountAllArticleRead() {
        return articleMapper.CountAllArticleRead();
    }

}




