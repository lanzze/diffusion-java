package com.aslan.module.cipher;

import com.aslan.module.core.Versioned;

import java.nio.channels.FileChannel;

public interface FileInfoHandler extends Versioned {

    int read(FileChannel channel, CipherInfo info);

    int write(FileChannel channel, CipherInfo info);

    int version();

    int length();
}