package com.aslan.module.cipher.keys.diffusion;

import com.aslan.module.cipher.AlgorithmInfo;
import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.cipher.algorithms.Box;
import com.aslan.module.cipher.keys.AbstractKey;
import com.aslan.module.utils.Utils;

public abstract class DiffusionC2 extends AbstractKey {
    protected byte[] S = Box.V2.ENC_BOX;
    protected int N = 0;
    protected int P = 0;
    protected int R = 0;
    protected int HH = 0;
    protected byte[] K;

    @Override
    public void init(CipherInfo cipherInfo, AlgorithmInfo algorithmInfo) {
        boolean alloc = N != algorithmInfo.N;
        init(algorithmInfo);
        if (alloc) alloc(algorithmInfo);
        init(cipherInfo);
        diffusion(K);
    }

    protected void alloc(AlgorithmInfo algorithmInfo) {
        K = new byte[N];
    }

    protected void init(AlgorithmInfo algorithmInfo) {
        N = algorithmInfo.R * algorithmInfo.N;
        int G = (int) Math.ceil(Utils.log2(N));
        P = 1 << G;
        HH = (int) Math.ceil(N * 1.0 / 4);
        R = G;
    }


    protected void init(CipherInfo cipherInfo) {
        int len = Math.min(N, cipherInfo.keyData.length);
        System.arraycopy(cipherInfo.keyData, 0, K, 0, len);
        for (int i = len, j = 1; i < N; i++) {
//            K[i] = (byte) j++;
            K[i] = 0;
        }
    }

    @Override
    public byte[] update() {
        return K;
    }

    public byte[] diffusion(byte[] K) {
        int a, b, c, d, p = 0, q = 0, H = N >> 1, PP = P >> 2, h = H - 1;
        for (int r = 0; r < R; r++) {
            for (int i = 0; i < HH; i++) {
                a = (p + i) % H;
                c = (q + i) % H + H;
                b = ((h + p - i) % H);
                d = ((h + q - i) % H) + H;
                K[a] = S[(K[a] ^ K[d] ^ K[b]) & 0xFF];
                K[c] = S[(K[c] ^ K[a] ^ K[d]) & 0xFF];
                K[b] = S[(K[b] ^ K[c] ^ K[a]) & 0xFF];
                K[d] = S[(K[d] ^ K[b] ^ K[c]) & 0xFF];
            }
            p = 1 << r;
            q = PP >> r;
        }
        return K;
    }
}