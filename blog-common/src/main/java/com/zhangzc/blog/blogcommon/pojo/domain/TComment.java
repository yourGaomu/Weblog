package com.zhangzc.blog.blogcommon.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 评论表
 * @TableName t_comment
 */
@TableName(value ="t_comment")
@Data
public class TComment implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评论内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 邮箱
     */
    @TableField(value = "mail")
    private String mail;

    /**
     * 网站地址
     */
    @TableField(value = "website")
    private String website;

    /**
     * 评论所属的路由
     */
    @TableField(value = "router_url")
    private String routerUrl;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 最后一次更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 删除标志位：0：未删除 1：已删除
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    /**
     * 回复的评论 ID
     */
    @TableField(value = "reply_comment_id")
    private Long replyCommentId;

    /**
     * 父评论 ID
     */
    @TableField(value = "parent_comment_id")
    private Long parentCommentId;

    /**
     * 原因描述
     */
    @TableField(value = "reason")
    private String reason;

    /**
     * 1: 待审核；2：正常；3：审核未通过;
     */
    @TableField(value = "status")
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TComment other = (TComment) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getMail() == null ? other.getMail() == null : this.getMail().equals(other.getMail()))
            && (this.getWebsite() == null ? other.getWebsite() == null : this.getWebsite().equals(other.getWebsite()))
            && (this.getRouterUrl() == null ? other.getRouterUrl() == null : this.getRouterUrl().equals(other.getRouterUrl()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getReplyCommentId() == null ? other.getReplyCommentId() == null : this.getReplyCommentId().equals(other.getReplyCommentId()))
            && (this.getParentCommentId() == null ? other.getParentCommentId() == null : this.getParentCommentId().equals(other.getParentCommentId()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getMail() == null) ? 0 : getMail().hashCode());
        result = prime * result + ((getWebsite() == null) ? 0 : getWebsite().hashCode());
        result = prime * result + ((getRouterUrl() == null) ? 0 : getRouterUrl().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getReplyCommentId() == null) ? 0 : getReplyCommentId().hashCode());
        result = prime * result + ((getParentCommentId() == null) ? 0 : getParentCommentId().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", content=").append(content);
        sb.append(", avatar=").append(avatar);
        sb.append(", nickname=").append(nickname);
        sb.append(", mail=").append(mail);
        sb.append(", website=").append(website);
        sb.append(", routerUrl=").append(routerUrl);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", replyCommentId=").append(replyCommentId);
        sb.append(", parentCommentId=").append(parentCommentId);
        sb.append(", reason=").append(reason);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}