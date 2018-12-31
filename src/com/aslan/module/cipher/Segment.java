package com.aslan.module.cipher;

import com.aslan.module.core.Usable;

public interface Segment extends Usable {
    void init(SessionInfo option);

    int exec(byte[] in, int offset, int length);

    int exec(byte[] in, int inf, byte[] out, int ouf, int length);
}