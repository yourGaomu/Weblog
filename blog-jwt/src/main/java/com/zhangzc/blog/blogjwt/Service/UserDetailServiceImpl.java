package com.zhangzc.blog.blogjwt.Service;

import com.zhangzc.blog.blogcommon.Service.TCommentService;
import com.zhangzc.blog.blogcommon.Service.TUserRoleService;
import com.zhangzc.blog.blogcommon.Service.TUserService;
import com.zhangzc.blog.blogcommon.Utils.CommentUtil;
import com.zhangzc.blog.blogcommon.pojo.domain.TUser;
import com.zhangzc.blog.blogcommon.pojo.domain.TUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final TUserService userService;
    private final TUserRoleService userRoleService;
    private final TCommentService commentService;
    private final CommentUtil commentUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            TUser one = userService.lambdaQuery().eq(TUser::getUsername, username).one();
            TUserRole one1 = userRoleService.lambdaQuery().eq(TUserRole::getUsername, username).one();
            if (one == null) {
                throw new UsernameNotFoundException(username + "用户不存在");
            } else if (one.getIsDeleted().equals(1)) {
                throw new UsernameNotFoundException(username + "该用户已经删除");
            }
            return User.withUsername(one.getUsername())
                    .password(one.getPassword())
                    .authorities(one1.getRole())
                    .build();
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
