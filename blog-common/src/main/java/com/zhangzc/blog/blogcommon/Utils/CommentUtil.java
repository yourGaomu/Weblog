package com.zhangzc.blog.blogcommon.Utils;



import com.zhangzc.blog.blogcommon.Service.TBlogSettingsService;
import com.zhangzc.blog.blogcommon.pojo.domain.TBlogSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class CommentUtil {
    private final TBlogSettingsService tBlogSettingsService;
    /**
     * 是否开启评论敏感词过滤, 0:不开启；1：开启
     */
    private static Integer isCommentSensiWordOpen;

    /**
     * 是否开启评论审核, 0: 未开启；1：开启
     */
    private static Integer isCommentExamineOpen;

    @PostConstruct
    public void init() {
        TBlogSettings tBlogSettings = tBlogSettingsService.list().get(0);
        isCommentExamineOpen=tBlogSettings.getIsCommentExamineOpen();
        isCommentSensiWordOpen=tBlogSettings.getIsCommentSensiWordOpen();
    }

    public static Integer getIsCommentSensiWordOpen() {
        return isCommentSensiWordOpen;
    }

    public static Integer getIsCommentExamineOpen() {
        return isCommentExamineOpen;
    }

    public void setCommentExamineOpen(Integer isCommentExamineOpen) {
        CommentUtil.isCommentExamineOpen = isCommentExamineOpen;
    }

    public void setCommentSensiWordOpen(Integer isCommentSensiWordOpen) {
        CommentUtil.isCommentSensiWordOpen = isCommentSensiWordOpen;
    }
}
