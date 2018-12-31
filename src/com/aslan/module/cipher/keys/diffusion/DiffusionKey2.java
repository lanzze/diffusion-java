package com.aslan.module.cipher.keys.diffusion;

import com.aslan.module.cipher.AlgorithmInfo;
import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.cipher.keys.AbstractKey;
import com.aslan.module.cipher.keys.Box;
import com.aslan.module.utils.Utils;

public abstract class DiffusionKey2 extends AbstractKey {
    protected int N = 0;
    protected int P = 0;
    protected int R = 0;
    protected int HH = 0;
    protected byte[] S;
    protected byte[] K;

    {
        S = Box.V2.N4_2;
    }

    public void setBox(byte[] box) {
        S = box;
    }

    @Override
    public void init(CipherInfo cipherInfo, AlgorithmInfo algorithmInfo) {
        boolean alloc = N != algorithmInfo.N;
        init(algorithmInfo);
        if (alloc) alloc(algorithmInfo);
        init(cipherInfo);
        diffusion(K);
    }

    protected void alloc(AlgorithmInfo algorithmInfo) {
        K = new byte[R * N];
    }

    protected void init(AlgorithmInfo algorithmInfo) {
        N = algorithmInfo.R * algorithmInfo.N;
        int G = (int) Math.ceil(Utils.log2(N));
        P = 1 << G;
        HH = (int) Math.ceil(N * 1.0 / 4);
        R = G;

    }


    protected void init(CipherInfo cipherInfo) {
        byte[] key = cipherInfo.keyData;
        int x = 0, y = 0, L = key.length;
        for (int i = 0; i < R; i++) {
            K[i] = x >= L ? (byte) ++y : key[x++];
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
                K[a] = S[(K[d] ^ K[a] ^ K[b]) & 0xFF];
                K[c] = S[(K[a] ^ K[c] ^ K[d]) & 0xFF];
                K[b] = S[(K[c] ^ K[b] ^ K[a]) & 0xFF];
                K[d] = S[(K[b] ^ K[d] ^ K[c]) & 0xFF];
            }
            p = 1 << r;
            q = PP >> r;
        }
        return K;
    }

    public void diffusion(byte[] K, byte[] M) {
    }
}