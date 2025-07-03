package com.zhangzc.blog.blogcommon.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangzc.blog.blogcommon.pojo.domain.TTag;
import com.zhangzc.blog.blogcommon.Service.TTagService;
import com.zhangzc.blog.blogcommon.Mapper.TTagMapper;
import org.springframework.stereotype.Service;

/**
* @author 吃饭
* @description 针对表【t_tag(文章标签表)】的数据库操作Service实现
* @createDate 2025-06-21 16:23:13
*/
@Service
public class TTagServiceImpl extends ServiceImpl<TTagMapper, TTag>
    implements TTagService{

}




