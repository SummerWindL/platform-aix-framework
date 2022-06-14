package com.platform.aix.module.word2pdf;

import com.platform.aix.common.util.AsposeUtil;
import org.springframework.stereotype.Service;

/**
 * Word转pdf服务
 * @author Advance
 * @date 2022年05月29日 15:06
 * @since V1.0.0
 */
@Service
public class Word2PdfService {


    /**
     * wordToPdf
     * @param inputPath
     * @param outputPath
     * @return
     */
    public boolean word2PdfHandler(String inputPath,String outputPath){
       return AsposeUtil.wordToPdf(inputPath,outputPath);
    }
}
