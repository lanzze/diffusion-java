package com.aslan.module.cipher.algorithms.dc140731;

import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.algorithms.AbstractAlgorithm;
import com.aslan.module.cipher.algorithms.Box;

public class DC140713Algorithm1 extends AbstractAlgorithm {

    {
        this.S = Box.V1.ENC_BOX;
        this.$ = Box.V1.DEC_BOX;
    }

    public void enc(byte[] M, int o, byte[][] K) {
        int x = 0, v = R, H = N >> 1, h = H - 1;
        for (int i = 1; i <= R; i++) {
            byte[] U = K[i - 1];
            byte[] V = K[--v];
            for (int y = H; y < N; ) {
                M[o + y] = (byte) (S[(M[o + y] ^ U[o + y]) & 0xFF] ^ M[o + x]);
                M[o + x] = (byte) (S[(M[o + x] ^ V[o + x]) & 0xFF] ^ M[o + y]);
                x = (++x) & h;
                y++;
            }
            x = H >> i;
        }
    }

    public void dec(byte[] C, int o, byte[][] K) {
        int x = 0, v = R - 1, H = N >> 1, h = H - 1;
        for (int i = 0; i < R; i++, v--) {
            byte[] V = K[i];
            byte[] U = K[v];
            for (int y = H; y < N; ) {
                C[o + x] = (byte) ($[(C[o + x] ^ C[o + y]) & 0xFF] ^ V[o + x]);
                C[o + y] = (byte) ($[(C[o + x] ^ C[o + y]) & 0xFF] ^ U[o + y]);
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