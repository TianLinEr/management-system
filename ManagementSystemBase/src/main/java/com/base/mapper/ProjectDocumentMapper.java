package com.base.mapper;

import com.base.entity.ProjectDocument;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
public interface ProjectDocumentMapper extends BaseMapper<ProjectDocument> {

    /**
     * 查询项目id
     * @param documentId
     * @return
     */
    @Select("select project_id from project_document where document_id = #{documentId}")
    Integer selectProjectId(Integer documentId);}
