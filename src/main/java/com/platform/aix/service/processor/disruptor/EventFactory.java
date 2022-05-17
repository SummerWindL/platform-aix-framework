package com.platform.aix.service.processor.disruptor;

/**
 * @author Advance
 * @date 2022年05月17日 16:09
 * @since V1.0.0
 */
public class EventFactory<E> implements com.lmax.disruptor.EventFactory<SeriesDataEvent> {
    SeriesDataEvent seriesDataEvent = new SeriesDataEvent();
    @Override
    public SeriesDataEvent newInstance() {
        return seriesDataEvent.clone();
    }
}
