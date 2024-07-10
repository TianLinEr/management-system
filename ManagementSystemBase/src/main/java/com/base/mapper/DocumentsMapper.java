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


    /**
     * 删除文档
     * @param projectId
     * @param fileName
     * @param state
     */
    void delete(@Param("projectId") String projectId,
                @Param("fileName") String fileName,
                @Param("state") String state);

    /**
     * 恢复文档
     * @param projectId
     * @param fileName
     * @param state
     */
    void revoke(@Param("projectId") String projectId,
                @Param("fileName") String fileName,
                @Param("state") String state);

    /**
     * 查询文档
     * @param projectId
     * @param fileName
     * @return
     */
    DocumentDTO selectOne(@Param("projectId") String projectId,
                          @Param("fileName") String fileName);

    /**
     * 更新文档
     * @param documents
     */
    void update(@Param("document") Documents documents);

    /**
     * 查询项目文档
     * @param projectId
     * @return
     */
    List<DocumentDTO> selectByProjectId(String projectId);
}
