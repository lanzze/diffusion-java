
package com.aslan.module.cipher.algorithms.dc140731;

import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.algorithms.AbstractAlgorithm;
import com.aslan.module.cipher.algorithms.Box;

public class DC140713Algorithm2 extends AbstractAlgorithm {

    {
        this.S = Box.V1.ENC_BOX;
        this.$ = Box.V1.DEC_BOX;
    }

    public void enc(byte[] M, int o, byte[] K) {
        int i = 0, H = N >> 1, h = H - 1, hh = H >> 1;
        for (int r = 1; r <= R; r++) {
            int k = N * (r - 1);
            for (int j = H; j < N; ) {
                M[o + j] = (byte) (S[(M[o + i] ^ M[o + j]) & 0xFF] ^ K[k + j]);
                M[o + i] = (byte) (S[(M[o + i] ^ M[o + j]) & 0xFF] ^ K[k + i]);
                i = (++i) & h;
                j++;
            }
            i = H >> r;
            i = i == 0 ? (hh + 1) : i;
        }
    }

    public void dec(byte[] C, int o, byte[] K) {
        int H = N >> 1, i = (H >> 1) + 1, h = H - 1;
        for (int r = 0; r < R; r++) {
            int k = N * (R - r - 1);
            for (int j = H; j < N; ) {
                C[o + i] = (byte) ($[(C[o + i] ^ K[k + i]) & 0xFF] ^ C[o + j]);
                C[o + j] = (byte) ($[(C[o + j] ^ K[k + j]) & 0xFF] ^ C[o + i]);
                i = (++i) & h;
                j++;
            }
            i = (1 << r) & h;
        }
    }

    public int identity() {
        return Consts.ALGORITHMS.DC140713;
    }

    public int version() {
        return 2;
    }

}