package com.zhangzc.blog.blogcommon.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangzc.blog.blogcommon.Service.TUserService;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.pojo.domain.TUser;
import com.zhangzc.blog.blogcommon.Mapper.TUserMapper;

import org.springframework.stereotype.Service;

/**
* @author 吃饭
* @description 针对表【t_user(用户表)】的数据库操作Service实现
* @createDate 2025-06-12 16:17:33
*/
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {
}




