package com.zhangzc.blog.blogcommon.enums;

import com.zhangzc.blog.blogcommon.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {

    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR("10000", "出错啦，后台小哥正在努力修复中..."),

    // ----------- 业务异常状态码 -----------
    PRODUCT_NOT_FOUND("20000", "该产品不存在（测试使用）"),
    LOGIN_FAIL("30000","用户登录失败"),
    UNAUTHORIZED("20002", "无访问权限，请先登录！"),
    CATEGORY_NAME_IS_EXISTED("20003","分类不存在"),
    TARTICLECATEGORYREL_ID_NOTEXIT("20004","分类ID不存在"),
    SPEND_MEG_FAILED("20005","消息发送失败了"),
    SERCH_QQNUMBER_FAILE("20006","查找相关的QQ信息失败"),
    COMMENT_SENT_FAILE("20007","评论保存失败"),
    COMMENT_CONTAIN_SENSITIVE_WORD("20015", "评论内容中包含敏感词，请重新编辑后再提交"),
    COMMENT_WAIT_EXAMINE("20016", "评论已提交, 等待博主审核通过")
    ;

    // 异常码
    private String Code;
    // 错误信息
    private String Message;

}
