package com.platform.aix.common.datacommon.db.domain.dict;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月16日 15:16
 */
public final class DictEnums {
    // 性别枚举（示例）
    public enum Gender implements DictEnum {
        MALE("GENDER", "MALE", "男", "1", 1),
        FEMALE("GENDER", "FEMALE", "女", "2", 2);

        private final DictItem item;

        Gender(String dictCode, String itemCode,
               String itemName, String itemValue, int sort) {
            this.item = new DictItem();
            item.setDictCode(dictCode);
            item.setItemCode(itemCode);
            item.setItemName(itemName);
            item.setItemValue(itemValue);
            item.setSort(sort);
        }

        @Override public String getDictCode() { return item.getDictCode(); }
        @Override public String getItemCode() { return item.getItemCode(); }
        @Override public String getItemName() { return item.getItemName(); }
        @Override public String getItemValue() { return item.getItemValue(); }
        @Override public int getSort() { return item.getSort(); }
    }

    // 是否枚举（示例）
    public enum YesNo implements DictEnum {
        YES("YES_NO", "YES", "是", "0", 1),
        NO("YES_NO", "NO", "否", "1", 2);
        private final DictItem item;

        YesNo(String dictCode, String itemCode,
              String itemName, String itemValue, int sort) {
            this.item = new DictItem();
            item.setDictCode(dictCode);
            item.setItemCode(itemCode);
            item.setItemName(itemName);
            item.setItemValue(itemValue);
            item.setSort(sort);
        }

        @Override public String getDictCode() { return item.getDictCode(); }
        @Override public String getItemCode() { return item.getItemCode(); }
        @Override public String getItemName() { return item.getItemName(); }
        @Override public String getItemValue() { return item.getItemValue(); }
        @Override public int getSort() { return item.getSort(); }
    }

    // 邮箱验证状态 EmailVerificationStatus
//    public enum EmailVerificationStatus implements DictEnum {
//        PENDING("EmailVerificationStatus", "PENDING", "待处理", "01", 1),
//        VERIFIED("EmailVerificationStatus", "VERIFIED", "已验证", "02", 2),
//        EXPIRED("EmailVerificationStatus", "EXPIRED", "已过期", "03", 2);
//        private final DictItem item;
//
//        EmailVerificationStatus(String dictCode, String itemCode,
//              String itemName, String itemValue, int sort) {
//            this.item = new DictItem();
//            item.setDictCode(dictCode);
//            item.setItemCode(itemCode);
//            item.setItemName(itemName);
//            item.setItemValue(itemValue);
//            item.setSort(sort);
//        }
//
//        @Override public String getDictCode() { return item.getDictCode(); }
//        @Override public String getItemCode() { return item.getItemCode(); }
//        @Override public String getItemName() { return item.getItemName(); }
//        @Override public String getItemValue() { return item.getItemValue(); }
//        @Override public int getSort() { return item.getSort(); }
//    }
}
