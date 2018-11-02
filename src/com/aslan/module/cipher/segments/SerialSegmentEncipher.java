package com.aslan.module.cipher.segments;

public class SerialSegmentEncipher extends AbstractSegment {

    public int exec(byte[] in, int offset, int length) {
        int L = info.L, N = info.N;
        int round = (length >> L) >> 3;
        int remainder = (length >> L) & 7;
        for (int i = 0; i < round; i++) {
            algorithm.enc(in, offset, key.update());
            offset += N;
            algorithm.enc(in, offset, key.update());
            offset += N;
            algorithm.enc(in, offset, key.update());
            offset += N;
            algorithm.enc(in, offset, key.update());
            offset += N;
            algorithm.enc(in, offset, key.update());
            offset += N;
            algorithm.enc(in, offset, key.update());
            offset += N;
            algorithm.enc(in, offset, key.update());
            offset += N;
            algorithm.enc(in, offset, key.update());
            offset += N;
        }
        for (int i = 0; i < remainder; i++) {
            algorithm.enc(in, offset, key.update());
            offset += N;
        }
        return length;
    }

    public int exec(byte[] in, int inf, byte[] out, int ouf, int length) {
        int N = info.N;
        int H = N - fill, F = N;
        int round = (int) Math.floor(length / H) >> 2;
        int remainder = (int) Math.floor(length / H) & 3;
        for (int i = 0; i < round; i++) {
            System.arraycopy(in, inf, out, ouf, H);
            padding.padding(out, H, fill);
            algorithm.enc(out, ouf, key.update());
            inf += H;
            ouf += F;
            System.arraycopy(in, inf, out, ouf, H);
            padding.padding(out, H, fill);
            algorithm.enc(out, ouf, key.update());
            inf += H;
            ouf += F;
            System.arraycopy(in, inf, out, ouf, H);
            padding.padding(out, H, fill);
            algorithm.enc(out, ouf, key.update());
            inf += H;
            ouf += F;
            System.arraycopy(in, inf, out, ouf, H);
            padding.padding(out, H, fill);
            algorithm.enc(out, ouf, key.update());
            inf += H;
            ouf += F;
        }
        for (int i = 0; i < remainder; i++) {
            System.arraycopy(in, inf, out, ouf, H);
            padding.padding(out, H, fill);
            algorithm.enc(out, ouf, key.update());
            inf += H;
            ouf += F;
        }
        int lost = length % H;
        if (lost > 0) {
            System.arraycopy(in, inf, out, ouf, lost);
            padding.padding(out, lost, F - lost);
            algorithm.enc(out, ouf, key.update());
        }
        return (int) (Math.ceil(1.0 * length / H) * F);
    }
}