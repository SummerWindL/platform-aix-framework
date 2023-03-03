package com.platform.aix.module.rmi;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Advance
 * @date 2022年09月12日 17:44
 * @since V1.0.0
 */
public interface RMIParseDataService {
    public Map<String,Object> parseData(String filePath, Date batchDate, List<String> productIdList);
}
