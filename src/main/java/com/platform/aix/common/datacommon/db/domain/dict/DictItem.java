package com.platform.aix.common.datacommon.db.domain.dict;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月16日 15:06
 */
public class DictItem {
    private String dictCode;
    private String itemCode;
    private String itemName;
    private String itemValue;
    private Integer sort;

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
