package com.base.mapper;

import com.base.dto.DocumentDTO;
import com.base.entity.Documents;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
public interface DocumentsMapper extends BaseMapper<Documents> {


    void delete(@Param("projectId") String projectId,
                @Param("fileName") String fileName,
                @Param("state") String state);

    void revoke(@Param("projectId") String projectId,
                @Param("fileName") String fileName,
                @Param("state") String state);

    DocumentDTO selectOne(@Param("projectId") String projectId,
                          @Param("fileName") String fileName);

    @Select("select project_id from project_document where document_id = #{documentId}")
    Integer selectProjectId(Integer documentId);

    void update(@Param("document") Documents documents);

    List<DocumentDTO> selectByProjectId(String projectId);
}
