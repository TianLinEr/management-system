package com.service.service;

import com.base.dto.CommentDTO;
import com.base.entity.Comments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
public interface CommentsService extends IService<Comments> {

    /**
     * 获取所有评论
     * @return
     */
    List<Comments> getAll();

    /**
     * 获取指定对象id的评论
     * @param objectId
     * @param objectType
     * @return
     */
    List<Comments> getByObjectId(String objectId, Integer objectType);

    /**
     * 获取指定用户id的评论详情
     * @param commentId
     * @return
     */
    CommentDTO getDetail(String commentId);

    /**
     * 添加评论
     * @param comments
     */
    void addComment(Comments comments);

    /**
     * 逻辑删除评论
     * @param userId
     * @param commentIds
     */
    void deleteComment(String userId,List<Integer> commentIds);

    /**
     * 修改评论
     * @param userId
     * @param comments
     */
    void updateComment(String userId,Comments comments);

    /**
     * 恢复评论
     * @param userId
     * @param comments
     */
    void revokeComment(String userId,Comments comments);
}
