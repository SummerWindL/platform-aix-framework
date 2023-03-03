package com.platform.aix.mapper;

import com.platform.common.util.StringUtil;

/**
 * @author Advance
 * @date 2022年09月28日 9:19
 * @since V1.0.0
 */
public class MapperTest {
    public static void main(String[] args) {
        String mapperName = StringUtil.replaceOnce("MstCustomerInfoAddWithBLOBs", "com.joyintech.tams.common.base.entity.",
                "com.joyintech.tams.common.base.dao.") + "Mapper";
        System.out.println(mapperName);
    }
}
