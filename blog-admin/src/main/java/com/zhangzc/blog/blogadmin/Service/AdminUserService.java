package com.zhangzc.blog.blogadmin.Service;

import com.zhangzc.blog.blogadmin.Pojo.Vo.UpdateAdminUserPasswordReqVO;
import com.zhangzc.blog.blogcommon.Utils.R;

public interface AdminUserService {
    public R UpdataUserPassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO);
    public R FindeUserInfo();
}
