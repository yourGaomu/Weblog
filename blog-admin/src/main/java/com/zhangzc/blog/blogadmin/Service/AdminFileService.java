package com.zhangzc.blog.blogadmin.Service;

import com.zhangzc.blog.blogcommon.Utils.R;
import org.springframework.web.multipart.MultipartFile;

public interface AdminFileService {
    R uploadFile(MultipartFile file) throws Exception;
}
