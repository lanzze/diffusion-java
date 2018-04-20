package com.aslan.module.cipher;

import com.aslan.module.core.Versionable;

public interface TextInfoHandler extends Versionable {

    int read(byte[] buf, int offset, CipherInfo info);

    int write(byte[] buf, int offset, CipherInfo cipherInfo);

    int length();
}