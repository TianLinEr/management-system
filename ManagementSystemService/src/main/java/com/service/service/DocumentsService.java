package com.service.service;

import com.base.dto.DocumentDTO;
import com.base.entity.Documents;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.vo.DocumentVO;

import java.text.ParseException;
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
    void addDocument(String fileName, String url, String userId,String projectId);

    /**
     * 删除文档
     * @param userId
     * @param projectId
     * @param fileName
     */
    void delete(String userId,String projectId,String fileName);

    void delete(String userId,List<Integer> documentIds);

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
    List<DocumentVO> getDocumentByProjectId(String projectId);

    DocumentVO selById(String documentId);

    List<DocumentVO> getAll();
}
