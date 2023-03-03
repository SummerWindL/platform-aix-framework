package com.platform.aix.service.processor.disruptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Advance
 * @date 2022年05月17日 16:10
 * @since V1.0.0
 */
public class SeriesData<T> implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(SeriesData.class);
    private String deviceInfoStr; //存放各种格式的字符串
    private Object data;
    private Class<T> classT;

    public SeriesData() {
    }

    public SeriesData(Class<T> classT) {
        this.classT = classT;
    }

    public SeriesData(String deviceInfoStr, Object data, Class<T> classT) {
        this.deviceInfoStr = deviceInfoStr;
        this.data = data;
        this.classT = classT;
    }

    public SeriesData(String deviceInfoStr, Class<T> classT) {
        this.deviceInfoStr = deviceInfoStr;
        this.classT = classT;
    }

    public SeriesData(Object data, Class<T> classT) {
        this.data = data;
        this.classT = classT;
    }

    public SeriesData(String deviceInfoStr) {
        this.deviceInfoStr = deviceInfoStr;
    }

    public SeriesData(String deviceInfoStr, Object data) {
        this.deviceInfoStr = deviceInfoStr;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getDeviceInfoStr() {
        return deviceInfoStr;
    }

    public void setDeviceInfoStr(String deviceInfoStr) {
        this.deviceInfoStr = deviceInfoStr;
    }

    public Class<T> getClassT() {
        return classT;
    }

    public void setClassT(Class<T> classT) {
        this.classT = classT;
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
