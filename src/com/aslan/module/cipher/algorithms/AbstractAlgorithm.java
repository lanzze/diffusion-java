package com.aslan.module.cipher.algorithms;

import com.aslan.module.cipher.Algorithm;
import com.aslan.module.cipher.AlgorithmInfo;

public abstract class AbstractAlgorithm implements Algorithm {

    protected int N;
    protected int R;
    protected int H;
    protected short[] BOX;

    public void init(AlgorithmInfo option) {
        this.BOX = option.BOX;
        this.N = option.N;
        this.R = option.R;
        this.H = this.N >> 1;
    }
}
