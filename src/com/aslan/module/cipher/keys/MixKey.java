package com.aslan.module.cipher.keys;

import com.aslan.module.cipher.AlgorithmInfo;
import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.utils.Utils;

import static com.aslan.module.cipher.keys.Box.*;

public abstract class MixKey extends AbstractKey {

    protected int N = 0;
    protected int R = 0;
    protected int H = 0;
    protected byte[][] K = null;

    // for mix
    protected int _N = 0;
    protected int _R = 0;
    protected int _H = 0;
    protected int _P = 0;

    public void init(CipherInfo cipherInfo, AlgorithmInfo algorithmInfo) {
        N = algorithmInfo.N;
        R = algorithmInfo.R;
        H = N >> 1;
        if (_N != R) {
            alloc(algorithmInfo);
        }
    }

    public byte[][] update() {
        return K;
    }

    protected void alloc(AlgorithmInfo algorithmInfo) {
        _N = algorithmInfo.R;
        _R = (int) Math.ceil(Utils.log2(algorithmInfo.R));
        _P = 1 << _R;
        _H = _N >> 1;
        _R++;
        byte[][] T = new byte[R][N];
        for (int i = 0; i < _N; i++) {
            T[i] = new byte[N];
        }
        K = T;
    }

    protected void init_key(byte[] key) {
        int length = key.length;
        int index = 0, x = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < N; j++) {
                int v = (x < length ? key[x] : index++ & 0xFF);
                K[i][j] = (byte) ((x & 1) == 0 ? S1[v] : S2[v]);
                x++;
            }
        }
    }

    protected byte[][] update_key() {
        int x = 0, X = _P >> 1;
        for (int i = 1; i <= _R; i++) {
            for (int y = _H; y < _N; y++) {
                mix(K[x], K[y]);
                x = ++x == _H ? 0 : x;
            }
            x = X >> i;
        }
        return K;
    }

    protected void mix(byte[] U, byte[] V) {
        int X = N;
        int x = 0, n = N - 1;
        for (int i = 1; i <= R; i++) {
            for (int y = 0; y < N; ) {
                U[y] = (byte) (S1[U[y] ^ V[y]] ^ S2[V[x]]);
                V[x] = (byte) (S2[V[x] ^ U[x]] ^ S1[U[y]]);
                x = ++x & n;
                y++;
            }
            x = X >> i;
        }
    }
}