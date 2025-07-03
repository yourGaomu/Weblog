package com.zhangzc.blog.blogadmin.Service.Impl;

import com.zhangzc.blog.blogadmin.Service.AdminFileService;
import com.zhangzc.blog.blogadmin.Utils.MinioUtil;
import com.zhangzc.blog.blogcommon.Utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminFileServiceImpl implements AdminFileService {
    private final MinioUtil minioUtil;


    @Override
    public R uploadFile(MultipartFile file) throws Exception {
        String url = minioUtil.uploadFile(file);
        Map<String, Object> map = new HashMap<>();
        map.put("url", url);
        return R.Success("上传成功",map);
    }
}
