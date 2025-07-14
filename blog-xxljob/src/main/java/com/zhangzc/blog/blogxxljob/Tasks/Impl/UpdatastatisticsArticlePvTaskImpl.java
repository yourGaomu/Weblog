package com.zhangzc.blog.blogxxljob.Tasks.Impl;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.zhangzc.blog.blogcommon.Service.TStatisticsArticlePvService;
import com.zhangzc.blog.blogcommon.Utils.TimeUtil;
import com.zhangzc.blog.blogcommon.pojo.domain.TStatisticsArticlePv;
import com.zhangzc.blog.blogxxljob.Tasks.UpdatastatisticsArticlePvTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class UpdatastatisticsArticlePvTaskImpl
        implements UpdatastatisticsArticlePvTask {
    private final TStatisticsArticlePvService pvService;
    private final TArticleService tArticleService;


    @XxlJob("task1")
    public ReturnT<String> task1() {
        System.out.println("开始了噢噢噢噢");
        return ReturnT.SUCCESS;
    }

    //应该每天的晚上24点获取，更新一下
    //TODO
    //这里还需要完善一下如果发生错误，应该写个报错注解,返回错误之后应该触发
    @XxlJob("updataTstatisticsArticlePv")
    public ReturnT<String> updataTstatisticsArticlePv() {
        try {
            //总浏览量
            Long pvTotalCount = tArticleService.CountAllArticleRead();
            pvService.save(TStatisticsArticlePv.builder()
                    .createTime(TimeUtil.getDateTime(LocalDate.now()))
                    .updateTime(TimeUtil.getDateTime(LocalDate.now()))
                    .pvCount(pvTotalCount)
                    .pvDate(TimeUtil.getDateTime(LocalDate.now()))
                    .build());
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            return ReturnT.FAIL;
        }

    }
}
