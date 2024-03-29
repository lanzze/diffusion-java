package com.aslan.module.cipher;

import com.aslan.module.core.Versioned;

public interface TextInfoHandler extends Versioned {

    int read(byte[] buf, int offset, CipherInfo info);

    int write(byte[] buf, int offset, CipherInfo cipherInfo);

    int length();
}