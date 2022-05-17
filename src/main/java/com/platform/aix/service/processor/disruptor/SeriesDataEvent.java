package com.platform.aix.service.processor.disruptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Advance
 * @date 2022年05月17日 16:11
 * @since V1.0.0
 */
public class SeriesDataEvent extends ValueWrapper<SeriesData> implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(SeriesDataEvent.class);

    public SeriesDataEvent clone(){
        try {
            return (SeriesDataEvent) super.clone();
        } catch (Exception e) {
            logger.warn("clone error.{}", e);
            return new SeriesDataEvent();
        }
    }
}
