package com.aplusstory.fixme;

public interface Recognizer {
    boolean checkCondition();
    boolean isEnabled();
    Object getExtraData();
    Class getExtraDataType();
    void enable();
    void disable();
    void destroy();
}
