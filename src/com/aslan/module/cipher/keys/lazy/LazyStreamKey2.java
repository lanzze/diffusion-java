package com.aslan.module.cipher.keys.lazy;

import com.aslan.module.cipher.AlgorithmInfo;
import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.keys.diffusion.DiffusionKey2;

public class LazyStreamKey2 extends DiffusionKey2 {
    int[] indices = null;

    public byte[][] update() {
        int i, v = 1, n = N - 1, _R = R - 1;
        for (int r = 0; r < R; r++) {
            i = indices[r] & n;
            v = v++ == _R ? 0 : v;
            byte[] X = K[r];
            byte[] Y = K[v];
            for (int j = 0; j < N; ) {
                Y[j] = (byte) (S1[(S1[(X[i] ^ Y[j]) & 0xFF] ^ Y[i]) & 0xFF]);
                X[i] = (byte) (S1[(S1[(X[i] ^ Y[j]) & 0xFF] ^ X[j]) & 0xFF]);
                i = ++i & n;
                j++;
            }
            indices[r] = (indices[r] << 1);
            indices[r] = (indices[r]) > N ? 1 : indices[r];
        }
        return K;
    }

    protected void alloc(AlgorithmInfo algorithmInfo) {
        super.alloc(algorithmInfo);
        indices = new int[algorithmInfo.R];
    }

    protected void init(CipherInfo cipherInfo) {
        super.init(cipherInfo);
        init_indices();
    }

    protected void init_indices() {
        indices[0] = N;
        for (int i = 1; i < R; i++) {
            indices[i] = 1 << i - 1;
        }
    }

    @Override
    public int identity() {
        return Consts.KEYS.STREAM_LAZY;
    }

    @Override
    public int version() {
        return 2;
    }
}