
package com.aslan.module.cipher.algorithms.dc140731;

import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.algorithms.AbstractAlgorithm;
import com.aslan.module.cipher.algorithms.Box;

public class DC140713Algorithm3 extends AbstractAlgorithm {

    {
        this.S = com.aslan.module.cipher.keys.Box.V2.S;
        this.$ = Box.V1.DEC_BOX;
    }

    public boolean show = false;


    public void enc(byte[] M, int o, byte[][] KK) {
        int a, b, c, d, p = 0, q = 0, H = N >> 1, HH = N >> 2, h = H - 1;
        for (int r = 0; r < R; r++) {
            byte[] K = KK[r];
            // if (show) {
            //     // System.out.print("r=" + (r - 1) + ",K= ");
            //     // Utils.print(K, true);
            //     // System.out.print("r=" + (r - 1) + ",M= ");
            //     // Utils.print(M, true);
            //     // System.out.println();
            // }
            for (int i = 0; i < HH; i++) {
                a = o + ((p + i) & h);
                c = o + ((q + i) & h) + H;
                b = o + ((h + p - i) & h);
                d = o + ((h + q - i) & h) + H;
                // System.out.println(String.format("i=%2d, %2d->%2d, %2d->%2d", i, a, b, c - H, d - H));
                M[c] = S[(S[(M[a] ^ M[c]) & 0xFF] ^ M[d] ^ K[c - o - H]) & 0xFF];
                M[b] = S[(S[(M[c] ^ M[b]) & 0xFF] ^ M[a] ^ K[b - o - 0]) & 0xFF];
                M[d] = S[(S[(M[b] ^ M[d]) & 0xFF] ^ M[c] ^ K[d - o - H]) & 0xFF];
                M[a] = S[(S[(M[d] ^ M[a]) & 0xFF] ^ M[b] ^ K[a - o - 0]) & 0xFF];
            }
            p = HH >> r;
            q = 1 << r;
            // Utils.print(M, "\n");
        }
        // if (show) {
        //     //            System.out.print("r=-" + ",M= ");
        //     // Utils.print(M, true);
        // }

    }

    public void dec(byte[] C, int o, byte[][] KK) {
        int a, b, c, d, p = 0, q = 0, H = N >> 1, HH = N >> 2, h = H - 1;
        for (int r = 0; r < R; r++) {
            byte[] K = KK[R - r - 1];
            for (int i = 0; i < HH; i++) {
                a = o + ((p + i) & h);
                c = o + ((q + i) & h) + H;
                b = o + ((h + p - i) & h);
                d = o + ((h + q - i) & h) + H;
                // System.out.println(String.format("%2d->%2d, %2d->%2d", a, b, c - H, d - H));
                C[a] = (byte) ($[($[C[a] & 0xFF] ^ C[b] ^ K[a - o - 0]) & 0xFF] ^ C[d]);
                C[d] = (byte) ($[($[C[d] & 0xFF] ^ C[c] ^ K[d - o - H]) & 0xFF] ^ C[b]);
                C[b] = (byte) ($[($[C[b] & 0xFF] ^ C[a] ^ K[b - o - 0]) & 0xFF] ^ C[c]);
                C[c] = (byte) ($[($[C[c] & 0xFF] ^ C[d] ^ K[c - o - H]) & 0xFF] ^ C[a]);
            }
            p = (1 << r) & h;
            q = HH >> r;
            // System.out.println("------------------------");
        }
    }

    public int identity() {
        return Consts.ALGORITHMS.DC140713;
    }

    public int version() {
        return 3;
    }
}