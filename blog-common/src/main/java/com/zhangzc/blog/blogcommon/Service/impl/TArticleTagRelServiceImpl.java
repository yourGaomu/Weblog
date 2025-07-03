package com.zhangzc.blog.blogcommon.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangzc.blog.blogcommon.pojo.domain.TArticleTagRel;
import com.zhangzc.blog.blogcommon.Service.TArticleTagRelService;
import com.zhangzc.blog.blogcommon.Mapper.TArticleTagRelMapper;
import org.springframework.stereotype.Service;

/**
* @author 吃饭
* @description 针对表【t_article_tag_rel(文章对应标签关联表)】的数据库操作Service实现
* @createDate 2025-06-21 15:08:41
*/
@Service
public class TArticleTagRelServiceImpl extends ServiceImpl<TArticleTagRelMapper, TArticleTagRel>
    implements TArticleTagRelService{

}




