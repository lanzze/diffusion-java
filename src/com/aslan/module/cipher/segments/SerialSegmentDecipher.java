package com.aslan.module.cipher.segments;

public class SerialSegmentDecipher extends AbstractSegment {

    public int run(byte[] IN, int inf, int length) {
        int L = this.info.L;
        int N = this.info.N;
        int round = length >> L >> 3;
        int remainder = length >> L & 7;
        for (int i = 0; i < round; i++) {
            algorithm.enc(IN, inf, key.update());
            inf += N;
            algorithm.enc(IN, inf, key.update());
            inf += N;
            algorithm.enc(IN, inf, key.update());
            inf += N;
            algorithm.enc(IN, inf, key.update());
            inf += N;
            algorithm.enc(IN, inf, key.update());
            inf += N;
            algorithm.enc(IN, inf, key.update());
            inf += N;
            algorithm.enc(IN, inf, key.update());
            inf += N;
            algorithm.enc(IN, inf, key.update());
            inf += N;
        }
        for (int i = 0; i < remainder; i++) {
            algorithm.enc(IN, inf, key.update());
            inf += N;
        }
        return length;
    }

    public int run(byte[] IN, int inf, byte[] OUT, int ouf, int length) {
        int N = this.info.N, L = this.info.L;
        int H = N - this.fill, F = N;
        int round = length >> L >> 2;
        int remainder = length >> L & 3;
        for (int i = 0; i < round; i++) {
            algorithm.dec(IN, inf, key.update());
            System.arraycopy(IN, inf, OUT, ouf, H);
            inf += F;
            ouf += H;
            algorithm.dec(IN, inf, key.update());
            System.arraycopy(IN, inf, OUT, ouf, H);
            inf += F;
            ouf += H;
            algorithm.dec(IN, inf, key.update());
            System.arraycopy(IN, inf, OUT, ouf, H);
            inf += F;
            ouf += H;
            algorithm.dec(IN, inf, key.update());
            System.arraycopy(IN, inf, OUT, ouf, H);
            inf += F;
            ouf += H;
        }
        for (int i = 0; i < remainder; i++) {
            algorithm.dec(IN, inf, key.update());
            System.arraycopy(IN, inf, OUT, ouf, H);
            inf += F;
            ouf += H;
        }
        return (int) (Math.ceil(1.0 * length / F) * H);
    }
}