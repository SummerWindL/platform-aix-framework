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
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
