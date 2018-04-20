package com.aslan.module.cipher;

public interface Segment {
    void init(SessionInfo option);

    int run(byte[] in, int offset, int length);

    int run(byte[] in, int inf, byte[] out, int ouf, int length);
}