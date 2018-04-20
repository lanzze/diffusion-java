package com.aslan.module.cipher;

public interface KeyFactory {

    Key make(CipherInfo option) throws Exception;
}