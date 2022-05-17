package com.platform.aix.service.processor.disruptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Advance
 * @date 2022年05月17日 16:10
 * @since V1.0.0
 */
public class SeriesData implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(SeriesData.class);
    private String deviceInfoStr;

    public SeriesData() {
    }

    public SeriesData(String deviceInfoStr) {
        this.deviceInfoStr = deviceInfoStr;
    }

    public String getDeviceInfoStr() {
        return deviceInfoStr;
    }

    public void setDeviceInfoStr(String deviceInfoStr) {
        this.deviceInfoStr = deviceInfoStr;
    }

    @Override
    public String toString() {
        return "SeriesData{" +
                "deviceInfoStr='" + deviceInfoStr + '\'' +
                '}';
    }

    public SeriesData clone() {
        try {
            return (SeriesData) super.clone();
        } catch (Exception e) {
            logger.warn("clone error.{}", e);
            return new SeriesData();
        }
    }
}
