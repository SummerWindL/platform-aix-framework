package com.platform.aix.module.cache;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Advance
 * @date 2022年05月25日 16:41
 * @since V1.0.0
 */
@Component
public class EntrustIdManager {
    private static final Logger logger = LoggerFactory.getLogger(EntrustIdManager.class);
    private static final String keySplit = "#";
    private static final String localEntrustPrefix = "l";
    private static final String entrustPrefix = "e";
    private static final String RTGSPrefix = "r";

    private RocksDB rocksDB;
    @Value("${agent.cache.path:cache}")
    private String cachePath;
    private Map<String, String> entrustIds = Maps.newConcurrentMap();
    private Map<String, String> localEntrustIds = Maps.newConcurrentMap();
    /**
     * 记录交收信息
     */
    private Map<String, String> reportNos = Maps.newConcurrentMap();
    private ExecutorService persistenceService = Executors.newSingleThreadExecutor();

    static {
        RocksDB.loadLibrary();
    }

    @PostConstruct
    public void init() {
        Options options = new Options();
        options.setCreateIfMissing(true);
        try {
            rocksDB = RocksDB.open(options, cachePath);
            RocksIterator rocksIterator = rocksDB.newIterator();
            for (rocksIterator.seekToFirst(); rocksIterator.isValid(); rocksIterator.next()) {
                String key = new String(rocksIterator.key());
                String value = new String(rocksIterator.value());
                if (key.startsWith(localEntrustPrefix)) {
                    localEntrustIds.put(key, value);
                } else if (key.startsWith(RTGSPrefix)) {
                    reportNos.put(key, value);
                } else {
                    entrustIds.put(key, value);
                }
            }
        } catch (Exception e) {
            logger.error("rocks db open path:{} error:{}", cachePath, e);
        }
    }

    public void clearCache() {
        rocksDB.close();
        File file = new File(cachePath);
        File[] fs = file.listFiles();
        for (File file1 : fs) {
            if (file1.delete()) {
                logger.info("delete file:{} success", file1.getName());
            } else {
                logger.info("delete file:{} failed", file1.getName());
            }
        }
        init();
    }

    private String getKey(String prefix, String accountId, String entrustId) {
        return prefix + accountId + keySplit + entrustId;
    }

    private void saveIdAsync(String key, String value) {
        persistenceService.submit(() -> {
            try {
                rocksDB.put(key.getBytes(), value.getBytes());
            } catch (Exception e) {
                logger.info("save id:{}, {} failed, {}", key, value, e);
            }
        });
    }

    public void setBrokeEntrustIdMapping(String accountId, String brokeEntrustId, String id) {
        String key = getKey(entrustPrefix, accountId, brokeEntrustId);
        String value = id;
        if (Strings.isNullOrEmpty(brokeEntrustId)) {
            logger.info("brokeEntrustId is empty: id = {}", id);
            return;
        }
        entrustIds.put(key, value);
        saveIdAsync(key, value);
    }

    public String getIdByBrokeEntrustId(String accountId, String brokeEntrustId) {
        if (Strings.isNullOrEmpty(brokeEntrustId)) {
            return null;
        }
        return entrustIds.get(getKey(entrustPrefix, accountId, brokeEntrustId));
    }

    public void setLocalEntrustIdMapping(String accountId, String localEntrustId, String id) {
        String key = getKey(localEntrustPrefix, accountId, localEntrustId);
        localEntrustIds.put(key, id);
        saveIdAsync(key, id);
    }

    public String getIdByLocalEntrustId(String accountId, String localEntrustId) {
        if (Strings.isNullOrEmpty(localEntrustId)) {
            return null;
        }
        return localEntrustIds.get(getKey(localEntrustPrefix, accountId, localEntrustId));
    }

    public void setReportNo(String accountId, String reportNo) {
        String key = getKey(RTGSPrefix, accountId, reportNo);
        if (Strings.isNullOrEmpty(reportNo)) {
            logger.info("reportNo is empty: id = {}", accountId);
            return;
        }
        reportNos.put(key, reportNo);
        saveIdAsync(key, reportNo);
    }

    public String getReportNoByRTGS(String accountId, String reportNo) {
        if (Strings.isNullOrEmpty(reportNo)) {
            return null;
        }
        return reportNos.get(getKey(RTGSPrefix, accountId, reportNo));
    }
}

