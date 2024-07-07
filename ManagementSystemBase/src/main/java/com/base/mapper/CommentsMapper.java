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

    @Select("select * from comments")
    List<Comments> getAll();

    @Select("select * from comments where object_id = #{objectId} and object_type = #{objectType}")
    List<Comments> getByObjectId(String objectId, Integer objectType);

    @Update("update comments set comment_state = #{state} where comment_id = #{commentId}")
    void deleteComment(String commentId,String state);

    @Update("update comments set comment_state = #{state} where comment_id = #{commentId}")
    void revokeComment(Integer commentId, String state);
}
