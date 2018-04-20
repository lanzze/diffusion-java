package com.aslan.module.cipher;

public interface AlgorithmFactory {

    Algorithm make(CipherInfo option) throws Exception;
}

