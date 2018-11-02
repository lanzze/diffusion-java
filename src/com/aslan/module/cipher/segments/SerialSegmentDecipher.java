package com.aslan.module.cipher.segments;

public class SerialSegmentDecipher extends AbstractSegment {

    public int exec(byte[] in, int inf, int length) {
        int L = info.L;
        int N = info.N;
        int round = length >> L >> 3;
        int remainder = length >> L & 7;
        for (int i = 0; i < round; i++) {
            algorithm.enc(in, inf, key.update());
            inf += N;
            algorithm.enc(in, inf, key.update());
            inf += N;
            algorithm.enc(in, inf, key.update());
            inf += N;
            algorithm.enc(in, inf, key.update());
            inf += N;
            algorithm.enc(in, inf, key.update());
            inf += N;
            algorithm.enc(in, inf, key.update());
            inf += N;
            algorithm.enc(in, inf, key.update());
            inf += N;
            algorithm.enc(in, inf, key.update());
            inf += N;
        }
        for (int i = 0; i < remainder; i++) {
            algorithm.enc(in, inf, key.update());
            inf += N;
        }
        return length;
    }

    public int exec(byte[] in, int inf, byte[] out, int ouf, int length) {
        int N = info.N, L = info.L;
        int H = N - fill, F = N;
        int round = length >> L >> 2;
        int remainder = length >> L & 3;
        for (int i = 0; i < round; i++) {
            algorithm.dec(in, inf, key.update());
            System.arraycopy(in, inf, out, ouf, H);
            inf += F;
            ouf += H;
            algorithm.dec(in, inf, key.update());
            System.arraycopy(in, inf, out, ouf, H);
            inf += F;
            ouf += H;
            algorithm.dec(in, inf, key.update());
            System.arraycopy(in, inf, out, ouf, H);
            inf += F;
            ouf += H;
            algorithm.dec(in, inf, key.update());
            System.arraycopy(in, inf, out, ouf, H);
            inf += F;
            ouf += H;
        }
        for (int i = 0; i < remainder; i++) {
            algorithm.dec(in, inf, key.update());
            System.arraycopy(in, inf, out, ouf, H);
            inf += F;
            ouf += H;
        }
        return (int) (Math.ceil(1.0 * length / F) * H);
    }
}