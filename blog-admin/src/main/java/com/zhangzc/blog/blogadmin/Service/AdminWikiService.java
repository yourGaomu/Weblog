package com.zhangzc.blog.blogadmin.Service;

import com.zhangzc.blog.blogadmin.Pojo.Vo.AddWikiReqVO;
import com.zhangzc.blog.blogcommon.Utils.R;

import java.util.Map;

public interface AdminWikiService {
    R addWiki(AddWikiReqVO addWikiReqVO);

    R deleteWiki(Map<String, String> deleteWikiReqVO);
}
