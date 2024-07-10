package com.base.mapper;

import com.base.entity.Comments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
public interface CommentsMapper extends BaseMapper<Comments> {

    /**
     * 获取所有评论
     * @return
     */
    @Select("select * from comments")
    List<Comments> getAll();

    /**
     * 获取指定对象id的评论
     * @param objectId
     * @param objectType
     * @return
     */
    @Select("select * from comments where object_id = #{objectId} and object_type = #{objectType}")
    List<Comments> getByObjectId(String objectId, Integer objectType);

    /**
     * 删除评论
     * @param commentId
     * @param state
     */
    @Update("update comments set comment_state = #{state} where comment_id = #{commentId}")
    void deleteComment(String commentId,String state);

    /**
     * 恢复评论
     * @param commentId
     * @param state
     */
    @Update("update comments set comment_state = #{state} where comment_id = #{commentId}")
    void revokeComment(Integer commentId, String state);
}
