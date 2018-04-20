package com.aslan.module.cipher.algorithms;

import com.aslan.module.cipher.Consts;

public class DC140713Algorithm_V1 extends AbstractAlgorithm {

    public void enc(byte[] M, int o, byte[][] K) {
        int x = 0, v = R, h = this.H - 1;
        for (int i = 1; i <= R; i++) {
            byte[] U = K[i - 1];
            byte[] V = K[--v];
            for (int y = this.H; y < N; ) {
                M[o + y] = (byte) (BOX[M[o + y] ^ U[o + y]] ^ M[o + x]);
                M[o + x] = (byte) (BOX[M[o + x] ^ V[o + x]] ^ M[o + y]);
                x = (++x) & h;
                y++;
            }
            x = H >> i;
        }
    }

    public void dec(byte[] C, int o, byte[][] K) {
        int x = 0, v = R - 1, h = this.H - 1;
        for (int i = 0; i < R; i++, v--) {
            byte[] V = K[i];
            byte[] U = K[v];
            for (int y = this.H; y < N; ) {
                C[o + x] = (byte) (BOX[C[o + x] ^ C[o + y]] ^ V[o + x]);
                C[o + y] = (byte) (BOX[C[o + x] ^ C[o + y]] ^ U[o + y]);
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
        return 1;
    }

}