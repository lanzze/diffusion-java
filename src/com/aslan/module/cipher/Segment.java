package com.aslan.module.cipher;

public interface Segment {
    void init(SessionInfo option);

    int exec(byte[] in, int offset, int length);

    int exec(byte[] in, int inf, byte[] out, int ouf, int length);
}