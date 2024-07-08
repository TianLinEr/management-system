package com.service.service;

import com.base.dto.DocumentDTO;
import com.base.entity.Documents;
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
public interface DocumentsService extends IService<Documents> {

    /**
     * 获取项目云端文件夹
     * @param projectId
     * @return
     */
    String getDir(String projectId);

    /**
     * 添加文档
     * @param fileName
     * @param url
     * @param userId
     */
    void addDocument(String fileName, String url, String userId);

    /**
     * 删除文档
     * @param userId
     * @param projectId
     * @param fileName
     */
    void delete(String userId,String projectId,String fileName);

    /**
     * 撤销删除文档
     * @param userId
     * @param projectId
     * @param fileName
     */
    void revoke(String userId,String projectId,String fileName);

    /**
     * 更新文档
     * @param userId
     * @param documents
     */
    void update(String userId,Documents documents);

    /**
     * 获取项目文档列表
     * @param projectId
     * @return
     */
    List<DocumentDTO> getDocumentByProjectId(String projectId);

}
