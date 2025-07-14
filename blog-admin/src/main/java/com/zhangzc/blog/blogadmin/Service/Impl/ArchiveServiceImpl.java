package com.zhangzc.blog.blogadmin.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindArchiveArticlePageListReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindArchiveArticlePageListRspVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindArchiveArticleRspVO;
import com.zhangzc.blog.blogadmin.Service.ArchiveService;
import com.zhangzc.blog.blogcommon.Service.TArticleService;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.pojo.domain.TArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArchiveServiceImpl implements ArchiveService {

    private final TArticleService tArticleService;

    @Override
    public PageResult findArchivePageList(FindArchiveArticlePageListReqVO findArchiveArticlePageListReqVO) {
        List<FindArchiveArticlePageListRspVO> result = new LinkedList<>();
        //先根据当前的page对象
        Page<TArticle> page = new Page<>(findArchiveArticlePageListReqVO.getCurrent(),
                findArchiveArticlePageListReqVO.getSize());
        //构建查询条件
        LambdaQueryWrapper<TArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TArticle::getCreateTime);
        Page<TArticle> page1 = tArticleService.page(page, wrapper);
        //然后获取实体类并且进行转换
        List<TArticle> records = page1.getRecords();
        List<FindArchiveArticleRspVO> articles = records.stream().map(record -> {
            //进行属性复制
            FindArchiveArticleRspVO vo = new FindArchiveArticleRspVO();
            BeanUtils.copyProperties(record, vo);

            vo.setCreateDate(record.getCreateTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());

            Date createTime = record.getCreateTime();
            // 转换为 YearMonth
            Instant instant = createTime.toInstant();
            ZoneId zoneId = ZoneId.systemDefault(); // 使用系统默认时区，可替换为 ZoneId.of("Asia/Shanghai")
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
            YearMonth createMonth = YearMonth.from(localDateTime);
            vo.setCreateMonth(createMonth);
            return vo;
        }).collect(Collectors.toList());
        //然后按照归档时期进行归类
        Map<YearMonth, List<FindArchiveArticleRspVO>> articlesByMonth = articles.stream()
                .filter(record -> record.getCreateMonth() != null) // 过滤掉月份为空的记录
                .collect(Collectors.groupingBy(
                        FindArchiveArticleRspVO::getCreateMonth,        // 按 createMonth 字段分组
                        Collectors.toList()                            // 每个分组内的元素收集为列表
                ));
        // 提取月份并手动倒序
        List<YearMonth> sortedMonths = new ArrayList<>(articlesByMonth.keySet());
        Collections.reverse(sortedMonths); // 反转顺序，变为降序

        // 构建结果列表
        for (YearMonth month : sortedMonths) {
            FindArchiveArticlePageListRspVO vo = new FindArchiveArticlePageListRspVO();
            vo.setMonth(month);
            vo.setArticles(articlesByMonth.get(month));
            result.add(vo);
        }

        return PageResult.Success(page,result);
    }
}
