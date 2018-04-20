package com.aslan.module.cipher.keys;

import com.aslan.module.cipher.AlgorithmInfo;
import com.aslan.module.cipher.CipherInfo;

import static com.aslan.module.cipher.keys.Box.*;

public class StreamKey_V1 extends MixKey {
    int[] indices = null;

    public void init(CipherInfo cipherInfo, AlgorithmInfo algorithmInfo) {
        super.init(cipherInfo, algorithmInfo);
        init_key(cipherInfo.key);
        update_key();
        update_key();
    }

    public byte[][] update() {
        int x = 0, v = R - 1, h = H - 1;
        for (int i = 0; i < R; i++, v--) {
            x = indices[i] & h;
            byte[] U = K[i];
            byte[] V = K[v];
            for (int y = H; y < N; ) {
                U[y] = (byte) (S1[(U[y] ^ V[y])] ^ U[x]);
                U[x] = (byte) (S2[(U[x] ^ V[x])] ^ U[y]);
                x = (++x) & h;
                y++;

            }
            indices[i] = (indices[i] << 1);
            indices[i] = (indices[i]) > H ? 1 : indices[i];
        }
        return K;
    }

    protected void alloc(AlgorithmInfo algorithmInfo) {
        super.alloc(algorithmInfo);
        indices = new int[algorithmInfo.R];
    }

    protected void init_key(byte[] key) {
        super.init_key(key);
        init_indices(N);
    }

    protected void init_indices(int N) {
        indices[0] = N;
        for (int i = 1; i <= R; i++) {
            indices[i] = 1 << i - 1;
        }
    }

    @Override
    public int level() {
        return 2;
    }

    @Override
    public int version() {
        return 1;
    }
}