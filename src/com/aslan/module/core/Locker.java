package com.aslan.module.core;

public class Locker implements Usable {
    private boolean lock;

    @Override
    public synchronized boolean lock() {
        if (!lock) return lock = true;
        return false;
    }

    @Override
    public synchronized void unlock() {
        lock = false;
    }
}
