package com.comment.controller;

import com.base.content.ContentBase;
import com.base.dto.CommentDTO;
import com.base.entity.Comments;
import com.base.utils.Result;
import com.service.service.CommentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "评论管理")
@Slf4j
public class CommentController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping("/all")
    @Operation(summary = "查询所有评论")
    public Result<Comments> getAll() {
        log.info("评论管理-查询所有评论-查询成功");
        return new Result<>(ContentBase.SuccessCode,"查询成功",
                commentsService.getAll());
    }

    @GetMapping("/all/{objectId}/{objectType}")
    @Operation(summary = "根据Id查询所有评论")
    public Result<Comments> getByObjectId(@PathVariable String objectId, @PathVariable Integer objectType) {
        log.info("评论管理-根据Id查询所有评论-查询成功");
        return new Result<>(ContentBase.SuccessCode,"查询成功",
                commentsService.getByObjectId(objectId,objectType));
    }

    @GetMapping("/detail/{commentId}")
    @Operation(summary = "查看评论详情")
    public Result<CommentDTO> getDetail(@PathVariable String commentId) {
        CommentDTO detail = commentsService.getDetail(commentId);
        ArrayList<CommentDTO> list = new ArrayList<>();
        list.add(detail);
        log.info("评论管理-查看评论详情-查询成功");
        return new Result<>(ContentBase.SuccessCode,"查询成功", list);
    }

    @PostMapping("/add")
    @Operation(summary = "添加评论")
    public Result addComment(@RequestBody Comments comments) {
        String userId = "1";
        //todo 获取用户id
        if(comments.getCommentId() == null)
            comments.setCommentId(Integer.valueOf(userId));

        commentsService.addComment(comments);
        log.info("评论管理-添加评论-添加成功");
        return new Result<>(ContentBase.SuccessCode,"添加成功", null);
    }

    @DeleteMapping("/delete/{commentId}")
    @Operation(summary = "删除评论")
    public Result deleteComment(@PathVariable String commentId) {
        String userId = "1";
        //todo 获取用户id
        commentsService.deleteComment(userId,commentId);
        log.info("评论管理-删除评论-删除成功");
        return new Result<>(ContentBase.SuccessCode,"删除成功", null);
    }

    @PutMapping("/update")
    @Operation(summary = "更新评论")
    public Result updateComment(@RequestBody Comments comments) {
        String userId = "1";
        //todo 获取用户id
        commentsService.updateComment(userId,comments);
        log.info("评论管理-更新评论-更新成功");
        return new Result<>(ContentBase.SuccessCode,"更新成功", null);
    }

    @PutMapping("/revoke")
    @Operation(summary = "撤销删除的评论")
    public Result revokeComment(@RequestBody Comments comments) {
        String userId = "1";
        //todo 获取用户id
        commentsService.revokeComment(userId,comments);
        log.info("评论管理-撤销删除的评论-撤销成功");
        return new Result<>(ContentBase.SuccessCode,"撤销成功", null);
    }

}
