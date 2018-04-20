package com.aslan.module.cipher;

import java.nio.channels.FileChannel;

public interface FileInfoHandlerFactory {

    int length();

    int read(FileChannel channel, CipherInfo info) throws Exception;

    int write(FileChannel channel, CipherInfo info) throws Exception;
}