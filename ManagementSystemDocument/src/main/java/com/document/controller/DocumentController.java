package com.document.controller;

import com.base.content.ContentBase;
import com.base.dto.DocumentDTO;
import com.base.entity.Documents;
import com.base.utils.Result;
import com.document.config.MinIOConfig;
import com.document.config.MinIOUtil;
import com.service.service.DocumentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@Tag(name = "文档管理")
public class DocumentController {

    @Autowired
    private DocumentsService documentsService;

    @Autowired
    private MinIOUtil minioUtils;

    /**
     * 根据Id获取文档
     * @param id
     * @return
     */
    @Operation(summary = "根据Id获取文档")
    @GetMapping("/{id}")
    Result<Documents> getById(@PathVariable String id){
        Documents documents = documentsService.getById(id);
        List<Documents> documentsList = new ArrayList<>();
        documentsList.add(documents);
        log.info("文档管理-根据Id获取文档-查询成功");
        return new Result<Documents>().success(ContentBase.SuccessCode, "查询成功", documentsList);
    }

    /**
     * 下载文件
     * @param fileName
     * @param response
     */
    @GetMapping("/download/{projectId}/{fileName}")
    @Operation(summary = "下载文件")
    public Result download(@PathVariable String projectId,@PathVariable String fileName, HttpServletResponse response) {
        try {
            String dir=documentsService.getDir(projectId);
            InputStream fileInputStream = minioUtils.getObject(dir, fileName);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
            return new Result<>().success(ContentBase.SuccessCode, "下载成功", null);
        } catch (Exception e) {
            log.error("下载失败");
            return new Result<>().error(ContentBase.ErrorCode, "下载失败");
        }
    }

    /**
     * 文件上传
     *
     * @param file
     */
    @PostMapping("/upload")
    @Operation(summary = "文件上传")
    public Result upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("project_id" ) String projectId) {
        try {
            String userId = "1";
            // todo 获取用户id

            String dir=documentsService.getDir(projectId);
//            String dir = "project-"+ projectId;
            minioUtils.createBucket(dir);
            //文件名
            String fileName = file.getOriginalFilename();
            //类型
            String contentType = file.getContentType();
            minioUtils.uploadFile(dir, file, fileName, contentType);
            log.info("文档管理-文件上传-成功");
            String url = minioUtils.getPresignedObjectUrl(dir, fileName);
            documentsService.addDocument(fileName,url,userId);
            return new Result<>().success(ContentBase.SuccessCode, "上传成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文档管理-文件上传-失败");
            return new Result<>().error(ContentBase.ErrorCode, "上传失败");
        }
    }

    /**
     * 删除
     *
     * @param fileName
     */
    @DeleteMapping("/{projectId}/{fileName}")
    @Operation(summary = "删除")
    public Result delete(@PathVariable String projectId,@PathVariable String fileName) {
        String userId = "1";
        // todo 获取用户id

        String dir=documentsService.getDir(projectId);
        minioUtils.removeFile(dir, fileName);
        log.info("文档管理-删除-成功");
        documentsService.delete(userId,projectId,fileName);
        return new Result<>().success(ContentBase.SuccessCode, "删除成功", null);
    }

    /**
     * 获取文件信息
     *
     * @param fileName
     * @return
     */
    @GetMapping("/info/{projectId}/{fileName}")
    @Operation(summary = "获取文件信息")
    public String getFileStatusInfo(@PathVariable String projectId,@PathVariable String fileName) {
        log.info("文档管理-获取文件信息-成功");
        String dir=documentsService.getDir(projectId);
        return minioUtils.getFileStatusInfo(dir, fileName);
    }

    /**
     * 撤销删除文档
     * @param projectId
     * @param fileName
     */
    @PostMapping("/revoke/{projectId}/{fileName}")
    @Operation(summary = "撤销删除文档")
    public Result revoke(@PathVariable String projectId,@PathVariable String fileName){
        String userId = "1";
        // todo 获取用户id

        documentsService.revoke(userId,projectId,fileName);
        log.info("文档管理-撤销删除文档-成功");
        return new Result<>().success(ContentBase.SuccessCode, "撤销成功", null);
    }

    /**
     * 更新文档
     * @param documents
     */
    @PutMapping("/update")
    @Operation(summary = "更新文档")
    public Result update(@RequestBody Documents documents){
        String userId = "1";
        // todo 获取用户id

        documentsService.update(userId,documents);
        log.info("文档管理-更新文档-成功");
        return new Result<>().success(ContentBase.SuccessCode, "更新成功", null);
    }

    /**
     * 获取项目文档列表
     * @param projectId
     * @return
     */
    @GetMapping("/all/{projectId}")
    @Operation(summary = "获取项目文档列表")
    public Result<DocumentDTO> getDocumentByProjectId(@PathVariable String projectId){
        List<DocumentDTO> document = documentsService.getDocumentByProjectId(projectId);
        log.info("文档管理-获取项目文档列表-成功");
        return new Result<DocumentDTO>().success(ContentBase.SuccessCode, "获取成功", document);
    }
}
