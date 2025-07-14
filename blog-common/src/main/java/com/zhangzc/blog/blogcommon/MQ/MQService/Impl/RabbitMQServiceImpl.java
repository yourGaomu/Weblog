package com.zhangzc.blog.blogcommon.MQ.MQService.Impl;

import com.zhangzc.blog.blogcommon.MQ.MQService.RabbitMQService;
import com.zhangzc.blog.blogcommon.Service.TArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.zhangzc.blog.blogcommon.pojo.domain.TArticle;

@Service
@RequiredArgsConstructor
public class RabbitMQServiceImpl implements RabbitMQService {

    private final TArticleService articleService;


    public Boolean addPointByArtID(String artID) {

        Long id = Long.valueOf(artID);
        if (artID.isEmpty()) {
            return false;
        }

        Boolean update = articleService.lambdaUpdate()
                .setSql("read_num = read_num + 1") // 设置SQL语句实现自增
                .eq(TArticle::getId, id)   // 根据文章ID筛选
                .update();
        return update;
    }
}


