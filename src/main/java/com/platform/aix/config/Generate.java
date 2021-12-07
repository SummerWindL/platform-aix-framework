package com.platform.aix.config;

import cn.smallbun.screw.core.engine.EngineFileType;
import lombok.Data;

/**
 * 配置
 * @author Advance
 * @date 2021年11月10日 14:26
 * @since V1.0.0
 */
@Data
public class Generate {
    /**
     * 立即生成数据库表文档
     */
    private boolean imGenTableDoc;

    /**
     * 生成的文件路径
     */
    private String fileOutputDir;

    /**
     * 模板类型
     */
    private EngineFileType engineFileType;

    /**
     * 模板名称
     */
    private String fileName;

    public EngineFileType getEngineFileType() {
        return engineFileType;
    }

    public void setEngineFileType(String engineFileType) {
        if(engineFileType.equals("HTML")){
            this.engineFileType = EngineFileType.HTML;
        }else if (engineFileType.equals("MD")){
            this.engineFileType = EngineFileType.MD;
        }else if (engineFileType.equals("WORD")){
            this.engineFileType = EngineFileType.WORD;
        }
    }
}
