package com.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.content.ContentBase;
import com.base.dto.DocumentDTO;
import com.base.dto.ProjectDTO;
import com.base.entity.Documents;
import com.base.entity.ProjectDocument;
import com.base.excepttion.AddDocumentException;
import com.base.excepttion.NoAuthorityUpdateDocumentException;
import com.base.excepttion.SqlSelectException;
import com.base.mapper.DocumentsMapper;
import com.base.mapper.ProjectDocumentMapper;
import com.base.utils.Result;
import com.base.vo.DocumentVO;
import com.base.vo.ProjectVO;
import com.http.client.ProjectHttp;
import com.http.client.TeamHttp;
import com.http.client.UserHttp;
import com.service.service.DocumentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
@Service
public class DocumentsServiceImpl extends ServiceImpl<DocumentsMapper, Documents> implements DocumentsService {

    @Autowired
    private DocumentsMapper documentsMapper;

    @Autowired
    private ProjectDocumentMapper projectDocumentMapper;

    @Autowired
    private ProjectHttp projectHttp;

    @Autowired
    private TeamHttp teamHttp;

    @Autowired
    private UserHttp userHttp;

    @Override
    public List<DocumentVO> getDocumentByProjectId(String projectId) {
        List<DocumentDTO> list = documentsMapper.selectByProjectId(projectId);
        List<DocumentVO> res = new ArrayList<>();
        list.forEach(documentDTO -> {
            DocumentVO documentVO = new DocumentVO(
                    documentDTO.getDocumentId(),
                    documentDTO.getDocumentName(),
                    documentDTO.getDocumentUrl(),
                    userHttp.getUserInfo(Collections.singletonList(documentDTO.getUserId())).getRes().get(0),
                    documentDTO.getCreateDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    documentDTO.getDocumentState(),
                    documentDTO.getProjectId(),
                    documentDTO.getDocumentType());
            res.add(documentVO);
        });
        return res;
    }

    @Override
    public DocumentVO selById(String documentId) {
        Documents documents = documentsMapper.selectOne(
                new LambdaQueryWrapper<Documents>().eq(Documents::getDocumentId, Integer.valueOf(documentId)));
        if (documents != null) {
            return new DocumentVO(
                    documents.getDocumentId(),
                    documents.getDocumentName(),
                    documents.getDocumentUrl(),
                    userHttp.getUserInfo(Collections.singletonList(documents.getUserId())).getRes().get(0),
                    documents.getCreateDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    documents.getDocumentState(),
                    null,
                    null);
        }

        return null;
    }

    @Override
    public List<DocumentVO> getAll() {
        List<DocumentDTO> list = documentsMapper.selectAll();
        List<DocumentVO> res = new ArrayList<>();
        list.forEach(documentDTO -> {
            DocumentVO documentVO = new DocumentVO(
                    documentDTO.getDocumentId(),
                    documentDTO.getDocumentName(),
                    documentDTO.getDocumentUrl(),
                    userHttp.getUserInfo(Collections.singletonList(documentDTO.getUserId())).getRes().get(0),
                    documentDTO.getCreateDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    documentDTO.getDocumentState(),
                    documentDTO.getProjectId(),
                    documentDTO.getDocumentType());
            res.add(documentVO);
        });
        return res;
    }

    @Override
    public String getDir(String projectId) {

        Result<ProjectVO> result = projectHttp.getById(projectId);
        List<ProjectVO> res = result.getRes();
        if (res.isEmpty())
            throw new SqlSelectException(ContentBase.ErrorCode);
        ProjectVO project = res.get(0);
        String createDate = project.getCreateDate();
        return createDate +"-"+ projectId;

    }

    @Override
    public void addDocument(String fileName, String url, String userId,String projectId) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Documents documents = new Documents(
                null, fileName, url,
                Integer.valueOf(userId),
                Timestamp.valueOf(localDateTime),
                String.valueOf(ContentBase.DocumentNotIsDel));
        int insert = documentsMapper.insert(documents);
        projectDocumentMapper.insert(new ProjectDocument(null, Integer.valueOf(projectId), documents.getDocumentId(), String.valueOf(ContentBase.DocumentIsPublic)));
        if (insert == 0)
            throw new AddDocumentException(ContentBase.ErrorCode);
    }

    @Override
    @Transactional
    public void delete(String userId, String projectId, String fileName) {

        DocumentDTO documentDTO = documentsMapper.selectOne(projectId, fileName);

        Integer userAuthority = userHttp.getUserAuthority(userId);
        if (!Objects.equals(userAuthority, ContentBase.AuthorityToAdmin)) {
            if (!userId.equals(documentDTO.getUserId())) {
                Result<ProjectVO> result = projectHttp.getById(projectId);
                List<ProjectVO> res = result.getRes();
                if (res.isEmpty())
                    throw new SqlSelectException(ContentBase.ErrorCode);

                ProjectVO projects = res.get(0);
                Integer authority = teamHttp.getUserAuthority(Integer.valueOf(userId), projects.getTeam().getTeamId());
                if (!Objects.equals(authority, ContentBase.AuthorityToDel))
                    throw new NoAuthorityUpdateDocumentException(ContentBase.ErrorCode);
                else
                    documentsMapper.delete(projectId, fileName, String.valueOf(ContentBase.DocumentIsDel));
            } else
                documentsMapper.delete(projectId, fileName, String.valueOf(ContentBase.DocumentIsDel));
        } else
            documentsMapper.delete(projectId, fileName, String.valueOf(ContentBase.DocumentIsDel));

    }

    @Override
    @Transactional
    public void delete(String userId, List<Integer> documentIds) {
        documentsMapper.deleteById(documentIds, String.valueOf(ContentBase.DocumentIsDel));

    }

    @Override
    @Transactional
    public void revoke(String userId, String projectId, String fileName) {

        DocumentDTO documentDTO = documentsMapper.selectOne(projectId, fileName);

        Integer userAuthority = userHttp.getUserAuthority(userId);
        if (!Objects.equals(userAuthority, ContentBase.AuthorityToAdmin)) {
            if (!userId.equals(documentDTO.getUserId())) {
                Result<ProjectVO> result = projectHttp.getById(projectId);
                List<ProjectVO> res = result.getRes();
                if (res.isEmpty())
                    throw new SqlSelectException(ContentBase.ErrorCode);

                ProjectVO projects = res.get(0);
                Integer authority = teamHttp.getUserAuthority(Integer.valueOf(userId), projects.getTeam().getTeamId());
                if (!Objects.equals(authority, ContentBase.AuthorityToDel))
                    throw new NoAuthorityUpdateDocumentException(ContentBase.ErrorCode);
                else
                    documentsMapper.revoke(projectId, fileName, String.valueOf(ContentBase.DocumentNotIsDel));
            } else
                documentsMapper.revoke(projectId, fileName, String.valueOf(ContentBase.DocumentNotIsDel));
        } else
            documentsMapper.revoke(projectId, fileName, String.valueOf(ContentBase.DocumentNotIsDel));

    }

    @Override
    @Transactional
    public void update(String userId, Documents documents) {
        Integer userAuthority = userHttp.getUserAuthority(userId);
        if (!Objects.equals(userAuthority, ContentBase.AuthorityToAdmin)) {
            if (!userId.equals(documents.getUserId())) {
                Integer integer = projectDocumentMapper.selectProjectId(documents.getDocumentId());
                Result<ProjectVO> result = projectHttp.getById(String.valueOf(integer));
                List<ProjectVO> res = result.getRes();
                if (res.isEmpty())
                    throw new SqlSelectException(ContentBase.ErrorCode);

                ProjectVO projects = res.get(0);
                Integer authority = teamHttp.getUserAuthority(Integer.valueOf(userId), projects.getTeam().getTeamId());
                if (!Objects.equals(authority, ContentBase.AuthorityToDel))
                    throw new NoAuthorityUpdateDocumentException(ContentBase.ErrorCode);
                else
                    documentsMapper.update(documents);
            } else
                documentsMapper.update(documents);
        } else
            documentsMapper.update(documents);
    }

    @Scheduled(cron = "* 40 23 * * ?")
    @Transactional
    public void deleteFile() {
        List<Documents> documents = documentsMapper.selectList(
                new LambdaQueryWrapper<Documents>().eq(Documents::getDocumentState, String.valueOf(ContentBase.DocumentIsDel))
        );
        documentsMapper.deleteBatchIds(documents.stream().map(Documents::getDocumentId).collect(Collectors.toList()));
    }
}
