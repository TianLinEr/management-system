package com.base.content;

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
}
