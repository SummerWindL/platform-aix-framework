package com.platform.aix.service.processor.disruptor;

/**
 * @author Advance
 * @date 2022年05月17日 16:09
 * @since V1.0.0
 */
public abstract class ValueWrapper<T> {
    private T value;

    public ValueWrapper() {}

    public ValueWrapper(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
