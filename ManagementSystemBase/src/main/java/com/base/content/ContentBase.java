package com.base.content;

/**
 * 一些固定常数内容
 */
public class ContentBase {

    /**
     * code
     */
    public static final Integer ErrorCode=500;
    public static final Integer SuccessCode=200;
    public static final Integer NotFound=404;

    /**
     * 项目是否公开
     */
    public static final Integer ProjectIsPublic=1;
    public static final Integer ProjectNotIsPublic=0;

    /**
     * 任务是否公开
     */
    public static final Integer TaskIsPublic=1;
    public static final Integer TaskNotIsPublic=0;

    /**
     * 文档是否公开
     */
    public static final Integer DocumentIsPublic=1;
    public static final Integer DocumentNotIsPublic=0;

    /**
     * 团队是否被删除
     */
    public static final Integer TeamIsDel=1;
    public static final Integer TeamNotIsDel=0;

    /**
     * 项目是否被删除
     */
    public static final Integer ProjectIsDel=1;
    public static final Integer ProjectNotIsDel=0;

    /**
     * 任务是否被删除
     */
    public static final Integer TaskIsDel=1;
    public static final Integer TaskNotIsDel=0;

    /**
     * 文档是否被删除
     */
    public static final Integer DocumentIsDel=1;
    public static final Integer DocumentNotIsDel=0;

    /**
     * 团队权限，是否可以删除
     */
    public static final Integer AuthorityToDel=0;
    public static final Integer AuthorityNotToDel=1;

    /**
     * 用户权限，管理员
     */
    public static final Integer AuthorityToUser=0;
    public static final Integer AuthorityToAdmin=1;

    /**
     * 被评论类型
     */
    public static final Integer ProjectType=0;
    public static final Integer TaskType=1;
    public static final Integer DocumentType=2;

    /**
     * 任务类型
     */
    public static final Integer TaskNew=0;
    public static final Integer TaskDo=1;
    public static final Integer TaskDone=2;

    /**
     * 评论是否被删除
     */
    public static final Integer CommentIsDel=1;
    public static final Integer CommentNotIsDel=0;
}
