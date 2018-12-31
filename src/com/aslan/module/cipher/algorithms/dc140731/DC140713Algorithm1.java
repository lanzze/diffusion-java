package com.aslan.module.cipher.algorithms.dc140731;

import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.algorithms.AbstractAlgorithm;
import com.aslan.module.cipher.algorithms.Box;

public class DC140713Algorithm1 extends AbstractAlgorithm {

    {
        this.S = Box.V1.ENC_BOX;
        this.$ = Box.V1.DEC_BOX;
    }

    public void enc(byte[] M, int o, byte[] K) {
        int x = 0, H = N >> 1, h = H - 1;
        for (int i = 1; i <= R; i++) {
            int u = N * i;
            int v = N * (R - i - 1);
            for (int y = H; y < N; ) {
                M[o + y] = (byte) (S[(M[o + y] ^ K[u + y]) & 0xFF] ^ M[o + x]);
                M[o + x] = (byte) (S[(M[o + x] ^ K[v + x]) & 0xFF] ^ M[o + y]);
                x = (++x) & h;
                y++;
            }
            x = H >> i;
        }
    }

    public void dec(byte[] C, int o, byte[] K) {
        int x = 0, H = N >> 1, h = H - 1;
        for (int i = 0; i < R; i++) {
            int v = N * i;
            int u = N * (R - i - 1);
            for (int y = H; y < N; ) {
                C[o + x] = (byte) ($[(C[o + x] ^ C[o + y]) & 0xFF] ^ K[v + x]);
                C[o + y] = (byte) ($[(C[o + x] ^ C[o + y]) & 0xFF] ^ K[u + y]);
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