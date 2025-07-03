package com.zhangzc.blog.blogadmin.Service;

import com.zhangzc.blog.blogadmin.Pojo.Vo.AddTagReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.DeleteTagReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindTagPageListReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.SearchTagsReqVO;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.R;

public interface AdminTagService {
    R addTags(AddTagReqVO addTagReqVO);

    R deleteTag(DeleteTagReqVO deleteTagReqVO);

    R searchTags(SearchTagsReqVO searchTagsReqVO);

    PageResult findTagPageList(FindTagPageListReqVO findTagPageListReqVO);

    R findTagSelectList();
}
