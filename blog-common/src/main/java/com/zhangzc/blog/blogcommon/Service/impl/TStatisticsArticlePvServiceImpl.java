package com.zhangzc.blog.blogcommon.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangzc.blog.blogcommon.pojo.domain.TStatisticsArticlePv;
import com.zhangzc.blog.blogcommon.Service.TStatisticsArticlePvService;
import com.zhangzc.blog.blogcommon.Mapper.TStatisticsArticlePvMapper;
import org.springframework.stereotype.Service;

/**
* @author 吃饭
* @description 针对表【t_statistics_article_pv(统计表 - 文章 PV (访问量))】的数据库操作Service实现
* @createDate 2025-06-24 15:52:14
*/
@Service
public class TStatisticsArticlePvServiceImpl extends ServiceImpl<TStatisticsArticlePvMapper, TStatisticsArticlePv>
    implements TStatisticsArticlePvService{

}




