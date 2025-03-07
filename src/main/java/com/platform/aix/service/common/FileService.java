package com.platform.aix.service.common;

import org.springframework.core.io.Resource;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Advance
 * @date 2022年06月06日 11:34
 * @since V1.0.0
 */
public interface FileService {
    /**
     * 上传文件
     * @param request
     * @param response
     * @param model
     * @return
     */
    Map<String,Object> uploadFile(HttpServletRequest request, HttpServletResponse response,
                                  ModelMap model) throws IOException;

    /**
     * 文件下载
     * @param response
     * @param fileUrl
     */
    void downLoadFile(HttpServletResponse response,String fileUrl);

    /**
     * 
     * @author jitwxs
     * @date 2025/3/7 17:20
     * @param filePath 
     * @return org.springframework.core.io.Resource
     */
    Resource loadFileAsResource(String filePath);
}
