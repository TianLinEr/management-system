package com.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.entity.Comments;
import com.base.mapper.CommentsMapper;
import com.service.service.CommentsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {

}
