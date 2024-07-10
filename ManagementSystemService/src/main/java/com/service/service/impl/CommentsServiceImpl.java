package com.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.content.ContentBase;
import com.base.dto.CommentDTO;
import com.base.entity.Comments;
import com.base.entity.Documents;
import com.base.entity.Projects;
import com.base.entity.Tasks;
import com.base.excepttion.AddCommentException;
import com.base.excepttion.NoAuthorityUpdateCommentException;
import com.base.excepttion.SqlSelectException;
import com.base.mapper.CommentsMapper;
import com.http.client.DocumentHttp;
import com.http.client.ProjectHttp;
import com.http.client.TaskHttp;
import com.http.client.UserHttp;
import com.service.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private ProjectHttp projectHttp;

    @Autowired
    private TaskHttp taskHttp;

    @Autowired
    private DocumentHttp documentHttp;

    @Autowired
    private UserHttp userHttp;

    @Override
    public List<Comments> getAll() {

        return commentsMapper.getAll();
    }

    @Override
    public List<Comments> getByObjectId(String objectId, Integer objectType) {

        return commentsMapper.getByObjectId(objectId, objectType);
    }

    @Override
    @Transactional
    public CommentDTO getDetail(String commentId) {

        LambdaQueryWrapper<Comments> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comments::getCommentId, commentId);
        Comments one = commentsMapper.selectOne(wrapper);
        CommentDTO commentDTO;
        if (one.getObjectType() == ContentBase.ProjectType) {
            commentDTO = new CommentDTO<Projects>();
            commentDTO.setObject(projectHttp
                    .getById(String.valueOf(one.getObjectId()))
                    .getRes()
                    .get(0));
        } else if (one.getObjectType() == ContentBase.TaskType) {
            commentDTO = new CommentDTO<Tasks>();
            commentDTO.setObject(taskHttp
                    .getById(String.valueOf(one.getObjectId()))
                    .getRes()
                    .get(0));
        } else if (one.getObjectType() == ContentBase.DocumentType) {
            commentDTO = new CommentDTO<Documents>();
            commentDTO.setObject(documentHttp
                    .getById(String.valueOf(one.getObjectId()))
                    .getRes()
                    .get(0));
        } else
            throw new SqlSelectException(ContentBase.ErrorCode);

        commentDTO.setCommentId(one.getCommentId());
        commentDTO.setCommentContent(one.getCommentContent());
        commentDTO.setCreateDate(one.getCreateDate());
        commentDTO.setUser(userHttp
                .getUserInfo(String.valueOf(one.getUserId()))
                .getRes()
                .get(0));
        commentDTO.setCommentState(one.getCommentState());

        return commentDTO;
    }

    @Override
    public void addComment(Comments comments) {
        comments.setCommentState(String.valueOf(ContentBase.CommentNotIsDel));
        LocalDateTime time = LocalDateTime.now();
        comments.setCreateDate(Timestamp.valueOf(time));
        int insert = commentsMapper.insert(comments);

        if (insert == 0)
            throw new AddCommentException(ContentBase.ErrorCode);
    }

    @Override
    @Transactional
    public void deleteComment(String userId, String commentId) {
        Integer userAuthority = userHttp.getUserAuthority(userId);
        Comments comments = commentsMapper.selectById(commentId);
        if (userAuthority != ContentBase.AuthorityToAdmin
                && userId != String.valueOf(comments.getUserId()))
            throw new NoAuthorityUpdateCommentException(ContentBase.ErrorCode);

        commentsMapper.deleteComment(commentId, String.valueOf(ContentBase.CommentIsDel));
    }

    @Override
    @Transactional
    public void updateComment(String userId, Comments comments) {
        Integer userAuthority = userHttp.getUserAuthority(userId);
        if (userAuthority != ContentBase.AuthorityToAdmin
                && userId != String.valueOf(comments.getUserId()))
            throw new NoAuthorityUpdateCommentException(ContentBase.ErrorCode);

        commentsMapper.update(comments,
                new LambdaQueryWrapper<Comments>()
                        .eq(Comments::getCommentId, comments.getCommentId())
                        .isNotNull(Comments::getCommentContent)
                        .isNotNull(Comments::getObjectId)
                        .isNotNull(Comments::getObjectType)
                        .isNotNull(Comments::getUserId));
    }

    @Override
    @Transactional
    public void revokeComment(String userId, Comments comments) {
        Integer userAuthority = userHttp.getUserAuthority(userId);
        if (userAuthority != ContentBase.AuthorityToAdmin
                && userId != String.valueOf(comments.getUserId()))
            throw new NoAuthorityUpdateCommentException(ContentBase.ErrorCode);

        commentsMapper.revokeComment(comments.getCommentId(), String.valueOf(ContentBase.CommentNotIsDel));
    }
}
