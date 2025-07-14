package com.zhangzc.blog.blogadmin.Service.Impl;

import com.zhangzc.blog.blogadmin.Pojo.Vo.AddWikiReqVO;
import com.zhangzc.blog.blogadmin.Service.AdminWikiService;
import com.zhangzc.blog.blogcommon.Service.TArticleService;
import com.zhangzc.blog.blogcommon.Service.TWikiCatalogService;
import com.zhangzc.blog.blogcommon.Service.TWikiService;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.Utils.TimeUtil;
import com.zhangzc.blog.blogcommon.enums.ResponseCodeEnum;
import com.zhangzc.blog.blogcommon.enums.WikiCatalogLevelEnum;
import com.zhangzc.blog.blogcommon.exception.exceptionClasses.BizException;
import com.zhangzc.blog.blogcommon.pojo.domain.TArticle;
import com.zhangzc.blog.blogcommon.pojo.domain.TWiki;
import com.zhangzc.blog.blogcommon.pojo.domain.TWikiCatalog;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminWikiServiceImpl implements AdminWikiService {

    private final TWikiService twikiService;
    private final TWikiCatalogService tWikiCatalogService;
    private final TArticleService tArticleService;

    @Override
    public R addWiki(AddWikiReqVO addWikiReqVO) {
        //先存入wiki库里面
        TWiki twiki = new TWiki();
        TWikiCatalog twikiCatalog = new TWikiCatalog();
        BeanUtils.copyProperties(addWikiReqVO, twiki);
        BeanUtils.copyProperties(addWikiReqVO, twikiCatalog);
        //获取当前的时间
        Date dateTime = TimeUtil.getDateTime(LocalDate.now());
        twiki.setCreateTime(dateTime);
        twiki.setUpdateTime(dateTime);
        twikiCatalog.setCreateTime(dateTime);
        twikiCatalog.setUpdateTime(dateTime);
        //存入Wiki库
        twikiService.save(twiki);
        twikiCatalog.setWikiId(twiki.getId());
        // 初始化默认目录
        //        // > 概述
        //        // > 基础
        twikiCatalog.setTitle("概述");
        twikiCatalog.setSort(1);
        tWikiCatalogService.save(twikiCatalog);
        twikiCatalog.setTitle("基础");
        twikiCatalog.setSort(2);
        tWikiCatalogService.save(twikiCatalog);
        return R.Success("添加成功",200);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R deleteWiki(Map<String, String> deleteWikiReqVO) {
        Long id = Long.valueOf(deleteWikiReqVO.get("id"));
        boolean b = twikiService.removeById(id);
        //不存在
        if (!b){
            throw new BizException(ResponseCodeEnum.WIKI_NOT_FOUND);
        }
        //查询该知识库中的目录
        List<TWikiCatalog> wikiCatalogDOS = tWikiCatalogService.lambdaQuery()
                .eq(TWikiCatalog::getWikiId, id).list();
        //获取目录的id
        List<Long> tWikiCatelogIds = wikiCatalogDOS.stream().map(TWikiCatalog::getId).collect(Collectors.toList());
        //获取文章id，把他变换为普通的文章
        List<Long> articleIds = wikiCatalogDOS.stream()
                .filter(wikiCatalogDO -> Objects.nonNull(wikiCatalogDO.getArticleId())  // 文章 ID 不为空
                        && Objects.equals(wikiCatalogDO.getLevel(), WikiCatalogLevelEnum.TWO.getValue())) // 二级目录
                .map(TWikiCatalog::getArticleId) // 提取文章 ID
                .collect(Collectors.toList());
        //不为空
        if (!articleIds.isEmpty()){
            tArticleService.lambdaUpdate()
                    .set(TArticle::getType,1)
                    .in(TArticle::getId, articleIds);
        }
        //删除目录
        tWikiCatalogService.removeByIds(tWikiCatelogIds);
        return R.Success("删除成功",200);
    }
}
