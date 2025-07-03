package com.zhangzc.blog.blogadmin.Service;

import com.zhangzc.blog.blogadmin.Pojo.Vo.UpdateBlogSettingsReqVO;
import com.zhangzc.blog.blogcommon.Utils.R;

public interface AdminBlogSettingsService {
    R updateBlogSettings(UpdateBlogSettingsReqVO updateBlogSettingsReqVO);

    R findDetail();
}
