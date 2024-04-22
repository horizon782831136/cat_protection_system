package com.liuwei.framework.interfaces;

public interface StateLifecycle<T extends Object> {
    T init();
    T alter();
    T halt();
    T except();
}
