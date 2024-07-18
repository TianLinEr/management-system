package com.document.controller;

import com.base.content.ContentBase;
import com.base.context.BaseContext;
import com.base.entity.Documents;
import com.base.excepttion.AddDocumentException;
import com.base.utils.Result;
import com.base.vo.DocumentVO;
import com.document.config.MinIOUtil;
import com.service.service.DocumentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
     * 根据项目Id获取文档
     * @param documentId
     * @return
     */
    @Operation(summary = "根据Id获取文档")
    @GetMapping("/sel/{documentId}")
    Result<DocumentVO> getById(@PathVariable String documentId){
        DocumentVO documents = documentsService.selById(documentId);
        List<DocumentVO> documentsList = new ArrayList<>();
        documentsList.add(documents);
        log.info("文档管理-根据Id获取文档-查询成功");
        return new Result<DocumentVO>().success(ContentBase.SuccessCode, "查询成功", documentsList);
    }

    /**
     * 下载文件
     *
     * @param fileName
     * @param response
     */
    @GetMapping("/download/{projectId}/{fileName}")
    @Operation(summary = "下载文件")
    public String download(@PathVariable String projectId, @PathVariable String fileName, HttpServletResponse response) {
        try {
            String dir = documentsService.getDir(projectId);
            try (InputStream fileInputStream = minioUtils.getObject(dir, fileName)) {
                String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
                response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
                response.setContentType("application/octet-stream"); // 更通用的MIME类型
                IOUtils.copy(fileInputStream, response.getOutputStream());
                response.flushBuffer(); // 确保所有数据都被发送
                log.info("文档管理-下载文件-成功");
            }
            return minioUtils.getPresignedObjectUrl(dir, fileName);
        } catch (Exception e) {
            log.error("下载失败", e);
            throw new RuntimeException(e);
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
                         @RequestParam("projectId" ) String projectId) {
        try {
            String userId = BaseContext.getCurrentId().toString();

            String dir=documentsService.getDir(projectId);
//            String dir = "project-"+ projectId;
            minioUtils.createBucket(dir);
            //文件名
            String fileName = file.getOriginalFilename();
            //类型
            String contentType = file.getContentType();
            if(minioUtils.isObjectExist(dir, fileName))
                throw new AddDocumentException(ContentBase.ErrorCode);

            minioUtils.uploadFile(dir, file, fileName, contentType);
            log.info("文档管理-文件上传-成功");
            String url = minioUtils.getPresignedObjectUrl(dir, fileName);
            documentsService.addDocument(fileName,url,userId,projectId);
            return new Result<>().success(ContentBase.SuccessCode, "上传成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文档管理-文件上传-失败");
            return new Result<>().error(ContentBase.ErrorCode, "上传失败,可能原因是该文件已存在，网络不稳定等");
        }
    }

    /**
     * 删除
     *
     * @param fileName
     */
    @DeleteMapping("/del/{projectId}/{fileName}")
    @Operation(summary = "删除")
    public Result delete(@PathVariable String projectId,@PathVariable String fileName) {
        String userId = BaseContext.getCurrentId().toString();

        String dir=documentsService.getDir(projectId);
        minioUtils.removeFile(dir, fileName);
        log.info("文档管理-删除-成功");
        documentsService.delete(userId,projectId,fileName);
        return new Result<>().success(ContentBase.SuccessCode, "删除成功", null);
    }

    /**
     * 删除
     *
     * @param documentIds
     */
    @DeleteMapping("/del/{documentIds}")
    @Operation(summary = "删除")
    public Result deleteById(@PathVariable List<Integer> documentIds) {
        String userId = BaseContext.getCurrentId().toString();
        log.info("文档管理-删除-成功");
        documentsService.delete(userId,documentIds);
        return new Result<>().success(ContentBase.SuccessCode, "删除成功", null);
    }

    @DeleteMapping("/del-project/{projectIds}")
    public Result deleteProjectIds(@PathVariable List<Integer> projectIds){
        projectIds.forEach(id->{
            String dir=documentsService.getDir(String.valueOf(id));
            minioUtils.removeBucket(dir);
        });
        log.info("文档管理-删除-成功");
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
        String userId = BaseContext.getCurrentId().toString();

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
        String userId = BaseContext.getCurrentId().toString();

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
    public Result<DocumentVO> getDocumentByProjectId(@PathVariable String projectId){
        List<DocumentVO> document = documentsService.getDocumentByProjectId(projectId);
        log.info("文档管理-获取项目文档列表-成功");
        return new Result<DocumentVO>().success(ContentBase.SuccessCode, "获取成功", document);
    }

    /**
     * 获取文档
     * @return
     */
    @GetMapping("/all")
    @Operation(summary = "查询所有")
    public Result<DocumentVO> getAll(){
        List<DocumentVO> document = documentsService.getAll();
        log.info("文档管理-查询所有-成功");
        return new Result<DocumentVO>().success(ContentBase.SuccessCode, "获取成功", document);
    }
}
