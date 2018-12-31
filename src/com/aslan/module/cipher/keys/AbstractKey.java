package com.aslan.module.cipher.keys;

import com.aslan.module.cipher.Key;
import com.aslan.module.core.Locker;

public abstract class AbstractKey implements Key {

    private Locker locker = new Locker();

    @Override
    public boolean lock() {
        return locker.lock();
    }

    @Override
    public void unlock() {
        locker.unlock();
    }
}