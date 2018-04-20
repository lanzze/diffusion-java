package com.aslan.module.cipher;

public interface TextInfoHandlerFactory {

    int length();

    int write(byte[] buf, int offset, CipherInfo info) throws Exception;

    int read(byte[] buf, int offset, CipherInfo info) throws Exception;
}