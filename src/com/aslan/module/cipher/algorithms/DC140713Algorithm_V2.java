
package com.aslan.module.cipher.algorithms;

import com.aslan.module.cipher.Consts;

public class DC140713Algorithm_V2 extends AbstractAlgorithm {

    public void enc(byte[] M, int o, byte[][] KK) {
        int x = 0, h = H - 1, hh = H >> 1;
        for (int i = 1; i <= R; i++) {
            byte[] K = KK[i - 1];
            for (int y = H; y < N; ) {
                M[o + y] = (byte) (BOX[M[o + x] ^ M[o + y]] ^ K[o + y]);
                M[o + x] = (byte) (BOX[M[o + x] ^ M[o + y]] ^ K[o + x]);
                x = (++x) & h;
                y++;
            }
            x = (x = H >> i) == 0 ? hh + 1 : x;
        }
    }

    public void dec(byte[] C, int o, byte[][] KK) {
        int x = (H >> 1) + 1, v = R - 1, h = H - 1;
        for (int i = 0; i < R; i++) {
            byte[] K = KK[v--];
            for (int y = H; y < N; ) {
                C[o + x] = (byte) (BOX[C[o + x] ^ K[o + x]] ^ C[o + y]);
                C[o + y] = (byte) (BOX[C[o + y] ^ K[o + y]] ^ C[o + x]);
                x = (++x) & h;
                y++;
            }
            x = (1 << i) & h;
        }
    }

    public int identity() {
        return Consts.ALGORITHMS.DC140713;
    }

    public int version() {
        return 2;
    }

}