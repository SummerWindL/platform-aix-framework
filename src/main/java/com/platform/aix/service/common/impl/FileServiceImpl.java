package com.platform.aix.service.common.impl;

import com.platform.aix.common.util.CacheUtil;
import com.platform.aix.service.common.FileService;
import com.platform.common.util.StringUtil;
import com.platform.core.constant.GlobalConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Advance
 * @date 2022年06月06日 11:35
 * @since V1.0.0
 */
@Service
public class FileServiceImpl implements FileService {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Value("${platform.aix.crosurl: http://101.35.80.154:7001}")
    private String crosurl;
    /**
     * 文件上传实现
     * @param request
     * @param response
     * @param model
     * @return
     */
    @Override
    public Map<String, Object> uploadFile(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        //获取缓存文件上传路径 缓存路径在项目一起动就获取 数据库表 SYS_PARA 数据
        String uploadFilePath = CacheUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, GlobalConstant.COM_PROP_UPLOAD_PATH);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        File fileMik = new File(uploadFilePath);
        if (!fileMik.exists()) {
            fileMik.mkdir();
        }
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            List<String> fileNames = new ArrayList<>();
            while (iter.hasNext()) {
                // 取得上传文件
//                MultipartFile file = multiRequest.getFile(iter.next());
                MultipartFile file = getFileFromRequest(multiRequest, iter.next());
                if (file != null) {
                    // 取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (!"".equals(myFileName.trim())) {
                        // 定义上传路径
                        String prexFileName = myFileName.substring(0,
                                myFileName.lastIndexOf(GlobalConstant.POINT));
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                        String path = uploadFilePath +File.separator + prexFileName
                                + GlobalConstant.UNDER_LINE + sdf.format(new Date())
                                + GlobalConstant.POINT
                                + myFileName.substring(
                                myFileName.lastIndexOf(GlobalConstant.POINT) + 1,
                                myFileName.length());
                        File localFile = new File(path);
                        file.transferTo(Paths.get(path));
                        fileNames.add(localFile.getName());
                        resultMap.put("path",path); //文件路径
                        resultMap.put("prexFileName",prexFileName); //上传路径
                    }
                }
            }
            // resultMap.put("fileIds", ids.substring(1, ids.length() - 1));
            resultMap.put("fileName", StringUtil.joinString(fileNames, GlobalConstant.COMMA)); //文件名
            return resultMap;
        }
        return null;
    }


    @Override
    public void downLoadFile(HttpServletResponse response, String fileUrl) {
        OutputStream ouputStream = null;
        InputStream fis = null;
        try {
            File file=new File(fileUrl);
            String filename=file.getName();
            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();

            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
//            String fileName = URLEncoder.encode(filename+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),"UTF-8");
            String fileName = new String(filename.getBytes("utf-8"), "utf-8");
            String contentDisposition = "attachment;filename*=UTF-8''"+ URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
            response.setHeader("Access-Control-Allow-Origin", this.crosurl); //跨域
            ouputStream = response.getOutputStream();
            ouputStream.write(buffer);
            ouputStream.flush();
            log.info("[文件下载成功] ======= [{}]",fileUrl.substring(fileUrl.indexOf("\\")+1));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件下载出现异常", e);
        }finally {
            if(null !=ouputStream){
                try {
                    ouputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Resource loadFileAsResource(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Resource resource = new UrlResource(path.toUri());
            if(resource.exists()) {
                return resource;
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static MultipartFile getFileFromRequest(MultipartHttpServletRequest multiRequest, String filePath) {
        if (multiRequest == null || StringUtil.isBlank(filePath)) {
            return null;
        }
        return multiRequest.getFile(filePath);
    }
}
