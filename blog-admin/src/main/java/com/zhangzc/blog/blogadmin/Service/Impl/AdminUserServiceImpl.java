package com.zhangzc.blog.blogadmin.Service.Impl;

import com.zhangzc.blog.blogadmin.Pojo.Vo.UpdateAdminUserPasswordReqVO;
import com.zhangzc.blog.blogadmin.Service.AdminUserService;
import com.zhangzc.blog.blogcommon.Service.TUserService;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.exception.exceptionClasses.BizException;
import com.zhangzc.blog.blogcommon.pojo.domain.TUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final TUserService userService;
    private final PasswordEncoder passwordEncoder;

    public R UpdataUserPassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO) {
        if (updateAdminUserPasswordReqVO.getPassword() == null || updateAdminUserPasswordReqVO.getUsername() == null) {
            throw new BizException("500", "用户名或者密码必须填写");
        }
        boolean update = userService.lambdaUpdate()
                .set(TUser::getPassword, passwordEncoder.encode(updateAdminUserPasswordReqVO.getPassword()))
                .eq(TUser::getUsername, updateAdminUserPasswordReqVO.getUsername()).update();
        if (update) {
            return R.Success("更新成功");
        }
        return R.Faile("更新失败");
    }

    public R FindeUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        return R.Success("查找成功",map);
    }

}
