package com.zhangzc.blog.blogcommon.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 博客设置表
 * @TableName t_blog_settings
 */
@TableName(value ="t_blog_settings")
@Data
public class TBlogSettings implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 博客Logo
     */
    @TableField(value = "logo")
    private String logo;

    /**
     * 博客名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 作者名
     */
    @TableField(value = "author")
    private String author;

    /**
     * 介绍语
     */
    @TableField(value = "introduction")
    private String introduction;

    /**
     * 作者头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * GitHub 主页访问地址
     */
    @TableField(value = "github_homepage")
    private String githubHomepage;

    /**
     * CSDN 主页访问地址
     */
    @TableField(value = "csdn_homepage")
    private String csdnHomepage;

    /**
     * Gitee 主页访问地址
     */
    @TableField(value = "gitee_homepage")
    private String giteeHomepage;

    /**
     * 知乎主页访问地址
     */
    @TableField(value = "zhihu_homepage")
    private String zhihuHomepage;

    /**
     * 博主邮箱地址
     */
    @TableField(value = "mail")
    private String mail;

    /**
     * 是否开启评论敏感词过滤, 0:不开启；1：开启
     */
    @TableField(value = "is_comment_sensi_word_open")
    private Integer isCommentSensiWordOpen;

    /**
     * 是否开启评论审核, 0: 未开启；1：开启
     */
    @TableField(value = "is_comment_examine_open")
    private Integer isCommentExamineOpen;

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
        TBlogSettings other = (TBlogSettings) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLogo() == null ? other.getLogo() == null : this.getLogo().equals(other.getLogo()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getAuthor() == null ? other.getAuthor() == null : this.getAuthor().equals(other.getAuthor()))
            && (this.getIntroduction() == null ? other.getIntroduction() == null : this.getIntroduction().equals(other.getIntroduction()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getGithubHomepage() == null ? other.getGithubHomepage() == null : this.getGithubHomepage().equals(other.getGithubHomepage()))
            && (this.getCsdnHomepage() == null ? other.getCsdnHomepage() == null : this.getCsdnHomepage().equals(other.getCsdnHomepage()))
            && (this.getGiteeHomepage() == null ? other.getGiteeHomepage() == null : this.getGiteeHomepage().equals(other.getGiteeHomepage()))
            && (this.getZhihuHomepage() == null ? other.getZhihuHomepage() == null : this.getZhihuHomepage().equals(other.getZhihuHomepage()))
            && (this.getMail() == null ? other.getMail() == null : this.getMail().equals(other.getMail()))
            && (this.getIsCommentSensiWordOpen() == null ? other.getIsCommentSensiWordOpen() == null : this.getIsCommentSensiWordOpen().equals(other.getIsCommentSensiWordOpen()))
            && (this.getIsCommentExamineOpen() == null ? other.getIsCommentExamineOpen() == null : this.getIsCommentExamineOpen().equals(other.getIsCommentExamineOpen()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLogo() == null) ? 0 : getLogo().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getAuthor() == null) ? 0 : getAuthor().hashCode());
        result = prime * result + ((getIntroduction() == null) ? 0 : getIntroduction().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getGithubHomepage() == null) ? 0 : getGithubHomepage().hashCode());
        result = prime * result + ((getCsdnHomepage() == null) ? 0 : getCsdnHomepage().hashCode());
        result = prime * result + ((getGiteeHomepage() == null) ? 0 : getGiteeHomepage().hashCode());
        result = prime * result + ((getZhihuHomepage() == null) ? 0 : getZhihuHomepage().hashCode());
        result = prime * result + ((getMail() == null) ? 0 : getMail().hashCode());
        result = prime * result + ((getIsCommentSensiWordOpen() == null) ? 0 : getIsCommentSensiWordOpen().hashCode());
        result = prime * result + ((getIsCommentExamineOpen() == null) ? 0 : getIsCommentExamineOpen().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", logo=").append(logo);
        sb.append(", name=").append(name);
        sb.append(", author=").append(author);
        sb.append(", introduction=").append(introduction);
        sb.append(", avatar=").append(avatar);
        sb.append(", githubHomepage=").append(githubHomepage);
        sb.append(", csdnHomepage=").append(csdnHomepage);
        sb.append(", giteeHomepage=").append(giteeHomepage);
        sb.append(", zhihuHomepage=").append(zhihuHomepage);
        sb.append(", mail=").append(mail);
        sb.append(", isCommentSensiWordOpen=").append(isCommentSensiWordOpen);
        sb.append(", isCommentExamineOpen=").append(isCommentExamineOpen);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}