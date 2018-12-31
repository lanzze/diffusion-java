package com.aslan.module.cipher.segments;

import com.aslan.module.cipher.*;
import com.aslan.module.core.Locker;

public abstract class AbstractSegment implements Segment {
    Key key;
    Padding padding;
    Algorithm algorithm;
    AlgorithmInfo info;
    int fill;
    private Locker locker = new Locker();

    public void init(SessionInfo option) {
        key = option.key;
        algorithm = option.algorithm;
        padding = option.padding;
        info = option.info;
        if (padding != null) {
            fill = padding.compute(info.N);
        }
    }

    @Override
    public boolean lock() {
        return locker.lock();
    }

    @Override
    public void unlock() {
        locker.unlock();
    }
}