
package com.aslan.module.cipher.algorithms.dc140731;

import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.algorithms.AbstractAlgorithm;
import com.aslan.module.cipher.algorithms.Box;
import com.aslan.module.utils.Utils;

public class DC140713Algorithm3 extends AbstractAlgorithm {

    {
        this.S = Box.V2.ENC_BOX;
        this.$ = Box.V2.DEC_BOX;
    }


    public void setBox(byte[] box) {
        this.S = box;
    }


    public void enc(byte[] M, int o, byte[] K) {
        int a, b, c, d, p = 0, q = 0, k = 0, H = N >> 1, HH = N >> 2, h = H - 1;
        for (int r = 0; r < R; r++, k += N) {
            for (int i = 0; i < HH; i++) {
                a = o + (p + i);
                c = o + (q + i) + H;
                b = o + ((h + p - i) & h);
                d = o + ((h + q - i) & h) + H;
                M[a] = S[(S[(M[a] ^ M[d]) & 0xFF] ^ M[b] ^ K[k + a - o]) & 0xFF];
                M[c] = S[(S[(M[c] ^ M[a]) & 0xFF] ^ M[d] ^ K[k + c - o]) & 0xFF];
                M[b] = S[(S[(M[b] ^ M[c]) & 0xFF] ^ M[a] ^ K[k + b - o]) & 0xFF];
                M[d] = S[(S[(M[d] ^ M[b]) & 0xFF] ^ M[c] ^ K[k + d - o]) & 0xFF];
            }
            p = (1 << r) & h;
            q = HH >> r;
//            System.out.print(String.format("第%d轮，r=%d，输出：\n", r + 1, r));
//            Utils.print(M, "", 16);
        }
    }

    public void dec(byte[] C, int o, byte[] K) {
        int a, b, c, d, p = 0, q = 0, H = N >> 1, HH = N >> 2, h = H - 1, k = N * (R - 1);
        for (int r = 0; r < R; r++, k -= N) {
            for (int i = 0; i < HH; i++) {
                a = o + (p + i);
                c = o + (q + i) + H;
                b = o + ((h + p - i) & h);
                d = o + ((h + q - i) & h) + H;
//                System.out.println(String.format("%2d->%2d, %2d->%2d: %3d", a, b, c - H, d - H, k));
                C[d] = (byte) ($[($[C[d] & 0xFF] ^ C[c] ^ K[k + d - o]) & 0xFF] ^ C[b]);
                C[b] = (byte) ($[($[C[b] & 0xFF] ^ C[a] ^ K[k + b - o]) & 0xFF] ^ C[c]);
                C[c] = (byte) ($[($[C[c] & 0xFF] ^ C[d] ^ K[k + c - o]) & 0xFF] ^ C[a]);
                C[a] = (byte) ($[($[C[a] & 0xFF] ^ C[b] ^ K[k + a - o]) & 0xFF] ^ C[d]);
            }
            q = (1 << r) & h;
            p = HH >> r;
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