package com.aslan.module.cipher.algorithms;

import com.aslan.module.cipher.Algorithm;
import com.aslan.module.cipher.AlgorithmInfo;

public abstract class AbstractAlgorithm implements Algorithm {

    protected int N;
    protected int R;
    protected byte[] S;
    protected byte[] $;

    public void init(AlgorithmInfo option) {
        N = option.N;
        R = option.R;
    }
}
