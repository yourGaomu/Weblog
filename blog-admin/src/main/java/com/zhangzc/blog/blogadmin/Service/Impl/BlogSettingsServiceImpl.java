package com.zhangzc.blog.blogadmin.Service.Impl;

import com.zhangzc.blog.blogadmin.Pojo.Vo.FindBlogSettingsDetailRspVO;
import com.zhangzc.blog.blogadmin.Service.BlogSettingsService;
import com.zhangzc.blog.blogcommon.Service.TBlogSettingsService;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.pojo.domain.TBlogSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogSettingsServiceImpl implements BlogSettingsService {

    private final TBlogSettingsService tBlogSettingsService;

    @Override
    public R findDetail() {
        FindBlogSettingsDetailRspVO resslult = new FindBlogSettingsDetailRspVO();
        TBlogSettings tBlogSettings = tBlogSettingsService.list().get(0);
        BeanUtils.copyProperties(tBlogSettings, resslult);
        return R.Success("查找成功",resslult);
    }
}
