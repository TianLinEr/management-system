package com.service.service.impl;

import com.base.entity.Tasks;
import com.base.mapper.TasksMapper;
import com.service.service.TasksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class TasksServiceImpl extends ServiceImpl<TasksMapper, Tasks> implements TasksService {

}
