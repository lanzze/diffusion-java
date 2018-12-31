package com.aslan.module.cipher.keys.lazy;

import com.aslan.module.cipher.AlgorithmInfo;
import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.keys.diffusion.DiffusionKey1;

public class LazyStreamKey1 extends DiffusionKey1 {
    int[] indices = null;

    public byte[] update() {
        int x, v = R - 1, h = H - 1;
        for (int i = 0; i < R; i++, v--) {
            x = indices[i] & h;
            byte[] U = KK[i];
            byte[] V = KK[v];
            for (int y = H; y < N; ) {
                U[y] = (byte) (S1[(U[y] ^ V[y])] ^ U[x]);
                U[x] = (byte) (S2[(U[x] ^ V[x])] ^ U[y]);
                x = (++x) & h;
                y++;
            }
            indices[i] = (indices[i] << 1);
            indices[i] = (indices[i]) > H ? 1 : indices[i];
        }
        for (int i = 0; i < R; i++) {
            System.arraycopy(KK[i], 0, K, i * N, N);
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
        for (int i = 1; i <= R; i++) {
            indices[i] = 1 << i - 1;
        }
    }

    @Override
    public int identity() {
        return Consts.KEYS.STREAM_LAZY;
    }

    @Override
    public int version() {
        return 1;
    }
}