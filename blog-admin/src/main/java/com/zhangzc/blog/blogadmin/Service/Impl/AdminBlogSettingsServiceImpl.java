package com.zhangzc.blog.blogadmin.Service.Impl;

import com.zhangzc.blog.blogadmin.Pojo.Vo.UpdateBlogSettingsReqVO;
import com.zhangzc.blog.blogadmin.Service.AdminBlogSettingsService;
import com.zhangzc.blog.blogcommon.Service.TBlogSettingsService;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.pojo.domain.TBlogSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminBlogSettingsServiceImpl implements AdminBlogSettingsService {
    private final TBlogSettingsService tBlogSettingsService;

    @Override
    public R updateBlogSettings(UpdateBlogSettingsReqVO updateBlogSettingsReqVO) {
        TBlogSettings tBlogSettings = new TBlogSettings();
        BeanUtils.copyProperties(updateBlogSettingsReqVO, tBlogSettings);
        tBlogSettings.setIsCommentSensiWordOpen(0);
        tBlogSettings.setIsCommentExamineOpen(0);
        if (updateBlogSettingsReqVO.getIsCommentExamineOpen())
            tBlogSettings.setIsCommentExamineOpen(1);
        if (updateBlogSettingsReqVO.getIsCommentSensiWordOpen()) {
            tBlogSettings.setIsCommentSensiWordOpen(1);
        }
        tBlogSettings.setId(1L);
        tBlogSettingsService.saveOrUpdate(tBlogSettings);
        return R.Success("更新或保存成功");
    }

    @Override
    public R findDetail() {
        TBlogSettings one = tBlogSettingsService.lambdaQuery().eq(TBlogSettings::getId, 1L).one();
        one.setId(null);
        return R.Success("查询成功",one);
    }
}
