package com.aslan.module.cipher.keys.diffusion;

import com.aslan.module.cipher.AlgorithmInfo;
import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.cipher.keys.Box;
import com.aslan.module.utils.Utils;

public abstract class DiffusionKey2 extends DiffusionKey1 {

    {
        S1 = Box.V2.S;
    }

    @Override
    protected void init(AlgorithmInfo algorithmInfo) {
        N = algorithmInfo.N;
        R = algorithmInfo.R;
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        _N = algorithmInfo.R;
        _G = (int) Math.ceil(Utils.log2(_N));
        _P = 1 << _G - 1;
        _H = (int) Math.ceil(_N * 1.0 / 2);
        _R = _G;
    }


    protected void init(CipherInfo cipherInfo) {
        byte[] key = cipherInfo.keyData;
        int x = 0, L = key.length;
        byte y = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < N; j++) {
                K[i][j] = x >= L ? ++y : key[x++];
            }
        }
    }

    protected byte[][] diffusion(byte[][] K) {
        int i = 0;
        for (int r = 1; r <= _R; r++) {
            for (int x = 0, j = _H; x < _H; x++) {
                diffusion(K[i], K[j]);
                i = ++i == _H ? 0 : i;
                j = ++j == _N ? _H : j;
            }
            i = _P >> r;
        }
        return K;
    }


    public void diffusion(byte[] X, byte[] Y) {
        int a = 0, b, c = 0, d, p = 0, q = 0, H = N >> 1, n = N - 1;
        for (int r = 0; r < R; r++) {
            for (int i = 0; i < N; i++) {
                b = (n + p - i) & n;
                d = (n + q - i) & n;
                Y[c] = S1[(Y[d] ^ X[a] ^ Y[c]) & 0xFF];
                X[b] = S1[(X[a] ^ Y[c] ^ X[b]) & 0xFF];
                Y[d] = S1[(Y[c] ^ X[b] ^ Y[d]) & 0xFF];
                // X[a] = S1[(X[b] ^ Y[d] ^ X[a]) & 0xFF];
                a = ++a & n;
                c = ++c & n;
            }
            p = a = N >> r + 1;
            q = c = 1 << r;
        }
    }
}