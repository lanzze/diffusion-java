package com.aslan.module.cipher.keys.diffusion;

import com.aslan.module.cipher.AlgorithmInfo;
import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.cipher.algorithms.Box;
import com.aslan.module.cipher.keys.AbstractKey;
import com.aslan.module.utils.Utils;

public abstract class DiffusionC1 extends AbstractKey {
    protected byte[] S = Box.V2.ENC_BOX;
    protected int N = 0;
    protected int R = 0;
    protected byte[] K;
    protected byte[] W;

    @Override
    public void init(CipherInfo cipherInfo, AlgorithmInfo algorithmInfo) {
        boolean alloc = N != algorithmInfo.N;
        init(algorithmInfo);
        if (alloc) alloc(algorithmInfo);
        init(cipherInfo);
        init(algorithmInfo.R);
    }

    protected void alloc(AlgorithmInfo algorithmInfo) {
        K = new byte[algorithmInfo.N * algorithmInfo.R];
        W = new byte[N];
    }

    protected void init(AlgorithmInfo algorithmInfo) {
        N = algorithmInfo.N;
        R = (int) Utils.log2(N);
    }


    protected void init(CipherInfo cipherInfo) {
        int len = Math.min(N, cipherInfo.keyData.length);
        System.arraycopy(cipherInfo.keyData, 0, W, 0, len);
        for (int i = len, j = 1; i < N; i++) {
            W[i] = 0;
        }
    }

    protected void init(int round) {
        for (int i = 0; i < round; i++) {
            System.arraycopy(diffusion(W), 0, K, i * N, N);
            Utils.print(W,"\n",16);
        }
    }


    @Override
    public byte[] update() {
        return K;
    }

    public byte[] diffusion(byte[] K) {
        int a, b, c, d, p = 0, q = 0, H = N >> 1, HH = N >> 2, h = H - 1;
        for (int r = 0; r < R; r++) {
            for (int i = 0; i < HH; i++) {
                a = p + i;
                c = q + i + H;
                b = ((h + p - i) & h);
                d = ((h + q - i) & h) + H;


//                K[a] = S[(K[a] ^ K[d] ^ K[b]) & 0xFF];
//                K[c] = S[(K[c] ^ K[a] ^ K[d]) & 0xFF];
//                K[b] = S[(K[b] ^ K[c] ^ K[a]) & 0xFF];
//                K[d] = S[(K[d] ^ K[b] ^ K[c]) & 0xFF];

                K[a] = S[(S[(K[a] | K[d]) & 0xff] ^ K[d] ^ K[b]) & 0xFF];
                K[c] = S[(S[(K[c] | K[a]) & 0xff] ^ K[a] ^ K[d]) & 0xFF];
                K[b] = S[(S[(K[b] | K[c]) & 0xff] ^ K[c] ^ K[a]) & 0xFF];
                K[d] = S[(S[(K[d] | K[b]) & 0xff] ^ K[b] ^ K[c]) & 0xFF];

            }
            p = (1 << r) & h;
            q = HH >> r;
        }

        return K;
    }
}