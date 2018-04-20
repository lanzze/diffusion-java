package com.aslan.views;

import com.aslan.module.random.SeedSource;

import java.awt.event.MouseEvent;

public class RandomPointSeedSource implements SeedSource {
    static int DEFAULT_BUFFER_SIZE = 1024 * 1024;
    byte[] buffer = null;
    int N = 0;
    int n = 0;
    int index = 0;
    int half = 0;
    int cap = 0;

    public RandomPointSeedSource() {
        super();
        this.init(RandomPointSeedSource.DEFAULT_BUFFER_SIZE);
    }

    protected void init(int size) {
        this.N = size;
        this.n = size - 1;
        this.half = size >> 1;
        this.buffer = new byte[size];
        for (int i = 0; i < size; i++) {
            this.buffer[i] = (byte) (Math.floor(Math.random() * 0xFF));
        }
        this.cap = size;
    }

    public void handleEvent(MouseEvent event) {
        long time = event.getWhen();
        int a = (int) (time >> 16 & 0xFF);
        int b = (int) (time >> 8 & 0xFF);
        int c = (int) (time >> 0 & 0xFF);
        int d = a * b * c;
        int e = ~a * ~b * ~c;
        int x = (event.getXOnScreen() + event.getX()) ^ d;
        int y = (event.getYOnScreen() + event.getY()) ^ e;
        this.set(x);
        this.set(y);
    }


    protected void set(int v) {
        this.buffer[this.index++] = (byte) (this.buffer[this.half--] ^ v & 0xFF);
        this.index &= this.n;
        this.half &= this.n;
    }

    public int pull(byte[] accept, int offset, int length) {
        int min, remainder = length;
        while (remainder > 0) {
            min = Math.min(remainder, this.cap);
            System.arraycopy(buffer, 0, accept, offset, min);
            offset += min;
            remainder -= min;
        }
        return length;
    }

    public float reliability() {
        return 0.9f;
    }

    public int capacity() {
        return this.N;
    }
}