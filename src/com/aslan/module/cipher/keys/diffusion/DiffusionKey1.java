package com.aslan.module.cipher.keys.diffusion;

import com.aslan.module.cipher.AlgorithmInfo;
import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.cipher.keys.AbstractKey;
import com.aslan.module.cipher.keys.Box;
import com.aslan.module.utils.Utils;


public abstract class DiffusionKey1 extends AbstractKey {

    protected int N = 0;
    protected int H = 0;
    protected int R = 0;
    protected byte[] K = null;
    protected byte[][] KK = null;
    protected byte[] S1;
    protected byte[] S2;

    // for diffusion
    protected int _N = 0;
    protected int _G = 0;
    protected int _R = 0;
    protected int _H = 0;
    protected int _P = 0;

    {
        this.S1 = Box.V1.S1;
        this.S2 = Box.V1.S2;
    }

    public void init(CipherInfo cipherInfo, AlgorithmInfo algorithmInfo) {
        boolean alloc = N != algorithmInfo.N;
        init(algorithmInfo);
        if (alloc) alloc(algorithmInfo);
        init(cipherInfo);
        diffusion(KK);
    }

    public byte[] update() {
        return K;
    }

    protected void alloc(AlgorithmInfo algorithmInfo) {
        K = new byte[N * R];
        KK = new byte[R][N];
    }

    protected void init(AlgorithmInfo algorithmInfo) {
        N = algorithmInfo.N;
        R = algorithmInfo.R;
        H = N >> 1;
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        _N = algorithmInfo.R;
        _G = (int) Math.ceil(Utils.log2(algorithmInfo.R));
        _P = 1 << _G;
        _H = _N >> 1;
        _R = _G + 1;
    }

    protected void init(CipherInfo cipherInfo) {
        byte[] key = cipherInfo.keyData;
        int length = key.length;
        int index = 0, x = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < N; j++) {
                int v = (x < length ? key[x] : index++ & 0xFF);
                KK[i][j] = ((x & 1) == 0 ? S1[v] : S2[v]);
                x++;
            }
        }
    }

    protected byte[][] diffusion(byte[][] K) {
        int x = 0, X = _P >> 1;
        for (int i = 1; i <= _R; i++) {
            for (int y = _H; y < _N; y++) {
                diffusion(K[x], K[y]);
                x = ++x == _H ? 0 : x;
            }
            x = X >> i;
        }
        for (int i = 0; i < R; i++) {
            System.arraycopy(KK[i], 0, K, i * N, N);
        }
        return K;
    }

    protected void diffusion(byte[] X, byte[] Y) {
        int i = 0, n = N - 1, P = N;
        for (int r = 1; r <= R; r++) {
            for (int j = 0; j < N; ) {
                X[j] = (byte) (S1[(X[j] ^ Y[j]) & 0xFf] ^ S2[Y[i] & 0xFF]);
                Y[i] = (byte) (S2[(Y[i] ^ X[i]) & 0xFF] ^ S1[X[j] & 0xFF]);
                i = ++i & n;
                j++;
            }
            i = P >> r;
        }
    }
}