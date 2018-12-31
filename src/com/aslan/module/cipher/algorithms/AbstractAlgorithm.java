package com.aslan.module.cipher.algorithms;

import com.aslan.module.cipher.Algorithm;
import com.aslan.module.cipher.AlgorithmInfo;
import com.aslan.module.core.Locker;

public abstract class AbstractAlgorithm implements Algorithm {

    protected int N;
    protected int R;
    protected byte[] S;
    protected byte[] $;
    private Locker locker = new Locker();

    public void init(AlgorithmInfo option) {
        N = option.N;
        R = option.R;
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
