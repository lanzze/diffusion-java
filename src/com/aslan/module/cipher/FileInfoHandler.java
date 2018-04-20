package com.aslan.module.cipher;

import com.aslan.module.core.Versionable;

import java.nio.channels.FileChannel;

public interface FileInfoHandler extends Versionable {

    int read(FileChannel channel, CipherInfo info);

    int write(FileChannel channel, CipherInfo info);

    int version();

    int length();
}