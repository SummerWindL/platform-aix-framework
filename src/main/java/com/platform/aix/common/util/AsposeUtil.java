package com.platform.aix.common.util;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Advance
 * @date 2022年05月29日 14:23
 * @since V1.0.0
 */
public class AsposeUtil {
    /**
     * 加载license 用于破解 不生成水印
     *
     * @author LCheng
     * @date 2020/12/25 13:51
     * @return
     */
    @SneakyThrows
    private static boolean getLicense() {
        boolean result = false;
        try (InputStream is = AsposeUtil.class.getClassLoader().getResourceAsStream("license.xml")) {
            License license = new License();
            license.setLicense(is);
            result = true;
        }
        return result;
    }

    /**
     * word转pdf
     *
     * @param wordPath word文件保存的路径
     * @param pdfPath  转换后pdf文件保存的路径
     * @author LCheng
     * @date 2020/12/25 13:51
     */
    @SneakyThrows
    public static boolean wordToPdf(String wordPath, String pdfPath) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return false;
        }
        FileOutputStream os = null;
        try {
            long old = System.currentTimeMillis();
            File file = new File(pdfPath); // 新建一个空白pdf文档
            os = new FileOutputStream(file);
            Document doc = new Document(wordPath); // Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            // EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            System.out.println("pdf转换成功，共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        wordToPdf("F:\\笔记本领用签字单.docx","F:\\11.pdf");
    }
}
