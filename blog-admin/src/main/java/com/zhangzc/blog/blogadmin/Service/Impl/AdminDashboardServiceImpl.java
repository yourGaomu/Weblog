package com.zhangzc.blog.blogadmin.Service.Impl;

import com.zhangzc.blog.blogadmin.Pojo.Vo.FindDashboardStatisticsInfoRspVO;
import com.zhangzc.blog.blogadmin.Service.AdminDashboardService;
import com.zhangzc.blog.blogcommon.Service.TArticleService;
import com.zhangzc.blog.blogcommon.Service.TCategoryService;
import com.zhangzc.blog.blogcommon.Service.TStatisticsArticlePvService;
import com.zhangzc.blog.blogcommon.Service.TTagService;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.exception.exceptionClasses.BizException;
import com.zhangzc.blog.blogcommon.pojo.domain.TArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final TStatisticsArticlePvService tStatisticsArticlePvService;
    private final TArticleService tArticleService;
    private final TCategoryService tCategoryService;
    private final TTagService tTagService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R findDashboardStatistics() {
        try {
            //文章总数
            Long couarticleTotalCountnt = tArticleService.lambdaQuery().count();
            //分类总数
            Long categoryTotalCount = tCategoryService.lambdaQuery().count();
            //标签总数
            Long tagTotalCount = tTagService.lambdaQuery().count();
            //总浏览量
            Long pvTotalCount = tArticleService.CountAllArticleRead();

            return R.Success("统计成功", FindDashboardStatisticsInfoRspVO.builder()
                    .articleTotalCount(couarticleTotalCountnt)
                    .tagTotalCount(tagTotalCount)
                    .pvTotalCount(pvTotalCount)
                    .categoryTotalCount(categoryTotalCount)
                    .build());
        } catch (Exception e) {
            return R.Faile("统计失败");
        }
    }
}
