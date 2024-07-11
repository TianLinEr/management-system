package com.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.content.ContentBase;
import com.base.dto.DocumentDTO;
import com.base.dto.ProjectDTO;
import com.base.entity.Documents;
import com.base.excepttion.AddDocumentException;
import com.base.excepttion.NoAuthorityUpdateDocumentException;
import com.base.excepttion.SqlSelectException;
import com.base.mapper.DocumentsMapper;
import com.base.mapper.ProjectDocumentMapper;
import com.base.utils.Result;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
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
    public List<DocumentDTO> getDocumentByProjectId(String projectId){
        return documentsMapper.selectByProjectId(projectId);
    }

    @Override
    public String getDir(String projectId) {
        Result<ProjectDTO> result = projectHttp.getById(projectId);
        List<ProjectDTO> res = result.getRes();
        if (res.isEmpty())
            throw new SqlSelectException(ContentBase.ErrorCode);
        ProjectDTO project = res.get(0);
        Timestamp date = project.getCreateDate();
        return date.getTime() + projectId;
    }

    @Override
    public void addDocument(String fileName, String url, String userId) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Documents documents = new Documents(
                null, fileName, url,
                Integer.valueOf(userId),
                new Timestamp(localDateTime.getLong(ChronoField.MILLI_OF_SECOND)),
                String.valueOf(ContentBase.DocumentNotIsDel));
        int insert = documentsMapper.insert(documents);
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
                Result<ProjectDTO> result = projectHttp.getById(projectId);
                List<ProjectDTO> res = result.getRes();
                if (res.isEmpty())
                    throw new SqlSelectException(ContentBase.ErrorCode);

                ProjectDTO projects = res.get(0);
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
                Result<ProjectDTO> result = projectHttp.getById(projectId);
                List<ProjectDTO> res = result.getRes();
                if (res.isEmpty())
                    throw new SqlSelectException(ContentBase.ErrorCode);

                ProjectDTO projects = res.get(0);
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
                Result<ProjectDTO> result = projectHttp.getById(String.valueOf(integer));
                List<ProjectDTO> res = result.getRes();
                if (res.isEmpty())
                    throw new SqlSelectException(ContentBase.ErrorCode);

                ProjectDTO projects = res.get(0);
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
