package com.platform.aix.controller;

import com.platform.aix.common.annotation.Access;
import com.platform.aix.common.annotation.AccessLimit;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.common.util.CacheUtil;
import com.platform.aix.module.word2pdf.Word2PdfService;
import com.platform.aix.service.common.FileService;
import com.platform.common.util.StringUtil;
import com.platform.core.constant.GlobalConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * word转pdf后台处理
 * @author Advance
 * @date 2022年05月29日 9:28
 * @since V1.0.0
 */
@RestController
@RequestMapping("/api/wod2Pdf")
@Access()
@CrossOrigin()
public class Word2PdfController {

    private static Logger log = LoggerFactory.getLogger(Word2PdfController.class);
    @Autowired
    private Word2PdfService word2PdfService;
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    @ResponseBody
    public APIResponse word2PdfRequest(MultipartFile file) {
        System.out.println(file.getName());
        return new APIResponse("文件上传成功！");
    }

    /**
     * 文件下载 此方法需要限流
     * @param response
     * @param fileUrl
     */
    @GetMapping("/downLoadFile")
    @ResponseBody
    @AccessLimit(limit = 1,sec = 10) //该接口访问限制 10秒一次 不生效
    public void download(HttpServletResponse response, @RequestParam String fileUrl ) {
        fileService.downLoadFile(response,fileUrl);
    }

    @PostMapping("/batchDownLoad")
    @ResponseBody
    @AccessLimit(limit = 1, sec = 30) // 增加接口限流
    public ResponseEntity<Resource> batchDownLoad(@RequestBody List<String> fileUrls,
                                                  HttpServletRequest request) throws IOException {
        // 创建带时间戳的临时文件
        String tempFileName = "batch_" + System.currentTimeMillis() + ".zip";
        File zipFile = new File(CacheUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY,
                GlobalConstant.COM_PROP_OUTPUT_PATH), tempFileName);

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            // 遍历文件路径列表
            for (String fileUrl : fileUrls) {
                File sourceFile = new File(fileUrl);
                if (!sourceFile.exists() || !sourceFile.isFile()) {
                    log.warn("文件不存在: {}", fileUrl);
                    continue;
                }

                try (FileInputStream fis = new FileInputStream(sourceFile)) {
                    ZipEntry entry = new ZipEntry(sourceFile.getName());
                    zos.putNextEntry(entry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
            }
        } catch (IOException e) {
            log.error("压缩文件创建失败", e);
            throw new IOException("文件打包失败");
        }

        // 设置响应头
        // 后端编码调整
        String encodedFileName = URLEncoder.encode("批量文件.zip", "UTF-8")
                .replace("+", "%20");  // 显式替换加号
        String contentDisposition = "attachment; filename*=UTF-8''" + encodedFileName;

        // 使用文件服务获取资源
        Resource resource = fileService.loadFileAsResource(zipFile.getAbsolutePath());

        // 后台删除临时文件
        new Thread(() -> {
            try {
                Files.deleteIfExists(zipFile.toPath());
            } catch (IOException ex) {
                log.warn("临时文件删除失败: {}", zipFile.getAbsolutePath());
            }
        }).start();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.parseMediaType("application/zip"))
                .body(resource);
    }


    @RequestMapping(value = "/wordUploadAndConvert2Pdf")
    @ResponseBody
    public APIResponse wordUploadAndConvert2Pdf(HttpServletRequest request, HttpServletResponse response,
                             ModelMap model) throws IOException {
        Map<String, Object> fileMap = fileService.uploadFile(request, response, model);
        if(ObjectUtils.nullSafeHashCode(fileMap) !=0){
            //存表
            String outputPath = CacheUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, GlobalConstant.COM_PROP_OUTPUT_PATH);
            //转换pdf
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String resultFile = outputPath+File.separator+fileMap.get("prexFileName").toString()+GlobalConstant.UNDER_LINE + sdf.format(new Date())+".pdf";
            word2PdfService.word2PdfHandler(fileMap.get("path").toString(),resultFile);
            return new APIResponse(resultFile); //返回PDF文件生成路径
        }
        return new APIResponse(null);
    }

}
