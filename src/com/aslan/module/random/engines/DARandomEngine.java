package com.aslan.module.random.engines;

import com.aslan.module.random.RandomEngine;
import com.aslan.module.utils.Utils;

import static com.aslan.module.random.Box.*;

public class DARandomEngine implements RandomEngine {
    static int DEFAULT_BUF_SIZE = 128;
    int N = 0;
    int H = 0;
    int R = 0;
    byte[] D = null;    //Seed array
    byte[] B = null;    //Buffer array
    int seedIndex = 0;
    int index = 0;

    public DARandomEngine() {
        init(DARandomEngine.DEFAULT_BUF_SIZE);
    }

    void init(int size) {
        N = size;
        B = new byte[size];
        D = new byte[size];
        H = N >> 1;
        R = (int) Utils.log2(size) + 1;
    }

    public void seed(byte[] seed, int offset, int length) {
        int min, remainder = Math.min(length, N), n = N - 1;
        while (remainder > 0) {
            min = Math.min(remainder, N - seedIndex);
            System.arraycopy(seed, offset, D, seedIndex, min);
            offset += min;
            remainder -= min;
            seedIndex = (seedIndex + min) & n;
        }
        update();
    }

    public int next(byte[] accept, int offset, int length) {
        int min = 0, remainder = length;
        while (remainder > 0) {
            min = Math.min(remainder, N);
            System.arraycopy(B, 0, accept, offset, min);
            offset += min;
            remainder -= min;
            update();
        }
        return length;
    }

    public byte rand() {
        byte value = B[index++];
        if (index >= N) {
            index = 0;
            update();
        }
        return value;
    }

    public int need() {
        return N;
    }


    protected void update() {
        int x = 0, r = R - 1, h = H - 1;
        for (int i = 0; i < r; i++) {
            for (int y = H; y < N; ) {
                B[y] = (byte) (S1[(B[y] ^ D[y]) & 0xFF] ^ B[x]);
                B[x] = (byte) (S2[(B[x] ^ D[x]) & 0xFF] ^ B[y]);
                x = (++x) & h;
                y++;
                B[y] = (byte) (S1[(B[y] ^ D[y]) & 0xFF] ^ B[x]);
                B[x] = (byte) (S2[(B[x] ^ D[x]) & 0xFF] ^ B[y]);
                x = (++x) & h;
                y++;
                B[y] = (byte) (S1[(B[y] ^ D[y]) & 0xFF] ^ B[x]);
                B[x] = (byte) (S2[(B[x] ^ D[x]) & 0xFF] ^ B[y]);
                x = (++x) & h;
                y++;
                B[y] = (byte) (S1[(B[y] ^ D[y]) & 0xFF] ^ B[x]);
                B[x] = (byte) (S2[(B[x] ^ D[x]) & 0xFF] ^ B[y]);
                x = (++x) & h;
                y++;

            }
            x = H >> i;
        }
    }
}