package com.aslan.module.cipher.segments;

import com.aslan.module.cipher.*;

public abstract class AbstractSegment implements Segment {
    Key key;
    Padding padding;
    Algorithm algorithm;
    AlgorithmInfo info;
    int fill;

    public void init(SessionInfo option) {
        key = option.key;
        algorithm = option.algorithm;
        padding = option.padding;
        info = option.info;
        if (padding != null) {
            fill = padding.compute(info.N);
        }
    }
}