package com.platform.aix.controller;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.platform.aix.common.response.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Pattern;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;

/**
 * 文件在线预览
 * @author Advance
 * @date 2022年06月08日 8:56
 * @since V1.0.0
 */
@RestController
@RequestMapping("/api")
public class PreviewDocumentController {

    private static final Logger logger = LoggerFactory.getLogger(PreviewDocumentController.class);


    @Value("${platform.aix.openofficeinstallationpath}")
    private String openOfficeInstallationPath;
    /**
     * @param request
     * @param response
     * @param filePath
     * @param fileName
     * @throws IOException
     */
    @Transactional
    @RequestMapping("/preview")
    @ResponseBody
    public APIResponse preview(HttpServletRequest request, HttpServletResponse response, @RequestParam String filePath, @RequestParam String fileName) throws IOException, InterruptedException {
        response.setContentType("text/html; charset=UTF-8");
        if(!"".equals(filePath)) {
            /* 根据项目所在的服务器环境,确定路径中的 /  和 \ */
            String osName = System.getProperty("os.name");
            if (Pattern.matches("Linux.*", osName)) {
                filePath.replace("\\","/");
            } else if(Pattern.matches("Windows.*", osName)) {
                filePath.replace("/","\\");
            }
            /* 获得文件名后缀 */
            String ext = "";
            if(!"".equals(fileName) && fileName.contains(".")){
                ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toUpperCase();
            }
            /* 根据文件类型不同进行预览 */
            /* 预览图片 */
            if ("PNG".equals(ext) || "JPEG".equals(ext) || "JPG".equals(ext)) {
                response.setContentType("image/jpeg");
            }
            /* 预览BMP格式的文件 */
            if ("BMP".equals(ext)) {
                response.setContentType("image/bmp");
            }
            /* 预览pdf */
            if ("PDF".equals(ext)) {
                response.setContentType("application/pdf");
            }
            /* 利用openOffice将office文件转换为pdf格式, 然后预览doc, docx, xls, xlsx, ppt, pptx */
            if ("DOC".equals(ext) || "DOCX".equals(ext) || "XLS".equals(ext) || "XLSX".equals(ext) || "PPT".equals(ext) || "PPTX".equals(ext)) {
                /* filePath在数据库中是不带文件后缀的, 由于jodConverter必须要识别后缀,所以将服务器中的文件重命名为带后缀的文件 */
                File docFile = new File(filePath);
                // File docFileWithExt = new File(filePath + "." + ext.toLowerCase()); //带后缀的文件
                // docFile.renameTo(docFileWithExt);
                /* 转换之后的文件名 */
                File pdfFile;
                if(filePath.contains(".")){
                    pdfFile = new File(filePath.substring(0, filePath.lastIndexOf(".")) + ".pdf");
                }else{
                    pdfFile = new File(filePath + ".pdf");
                }
                /* 判断即将要转换的文件是否真实存在 */
                if (docFile.exists()) {
                    /* 判断该文件是否已经被转换过,若已经转换则直接预览 */
                    if (!pdfFile.exists()) {
                        OpenOfficeConnection connection;
                        /* 打开OpenOffice连接 */
                        try {
                            connection = new SocketOpenOfficeConnection("127.0.0.1",8100);
                            connection.connect();
                        } catch (java.net.ConnectException e) {
                            logger.warn("openOffice未连接，正在重新连接...");
                            // 启动OpenOffice的服务
                            String command = openOfficeInstallationPath + "program/soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard";
                            Runtime.getRuntime().exec(command);
                            Thread.sleep(1000);
                            connection = new SocketOpenOfficeConnection(8100);
                            connection.connect();
                            logger.warn("openOffice重新连接成功!!!");
                        }
                        try{
                            // DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
                            DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
                            converter.convert(docFile, pdfFile);
                            connection.disconnect();
                            filePath = pdfFile.getPath(); // 文件转换之后的路径
                            response.setContentType("application/pdf");
                        }catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
                            e.printStackTrace(); // 读取转换文件失败
                            logger.info("读取转换文件失败!!!");
                            return new APIResponse("读取转换文件失败!!!");
                        }finally { // 发生exception时, connection不会自动切断, 程序会一直挂着
                            try{
                                if(connection != null){
                                    connection.disconnect();
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    } else {
                        filePath = pdfFile.getPath(); // 文件已经转换过
                        response.setContentType("application/pdf");
                    }
                } else {
                    logger.info("需要预览的文档在服务器中不存在!!!");
                    return new APIResponse("需要预览的文档在服务器中不存在!!!");
                }
            }
            /* 将文件写入输出流,显示在界面上,实现预览效果 */
            FileInputStream fis = new FileInputStream(filePath);
            OutputStream os = response.getOutputStream();
            try {
                int count;
                byte[] buffer = new byte[1024 * 1024];
                while ((count = fis.read(buffer)) != -1)
                    os.write(buffer, 0, count);
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (os != null)
                    os.close();
                if (fis != null)
                    fis.close();
            }
        }
        return null;
    }
}
