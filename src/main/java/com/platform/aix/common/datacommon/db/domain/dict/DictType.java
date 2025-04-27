package com.platform.aix.common.datacommon.db.domain.dict;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月16日 15:06
 */
public class DictType {
    private String dictCode;
    private String dictName;
    private Boolean isFixed;

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public Boolean getFixed() {
        return isFixed;
    }

    public void setFixed(Boolean fixed) {
        isFixed = fixed;
    }
}
