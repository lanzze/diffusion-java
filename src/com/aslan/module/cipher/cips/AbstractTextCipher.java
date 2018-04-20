package com.aslan.module.cipher.cips;

import com.aslan.module.cipher.*;
import com.aslan.module.cipher.paddings.RandomBlockPadding;

public abstract class AbstractTextCipher implements TextCipher {
    protected Padding padding = new RandomBlockPadding(null);
    protected Key key = null;
    protected Algorithm algorithm = null;
    protected AlgorithmInfo algorithmInfo = null;
    protected CipherInfo cipherInfo = null;

    protected int algorithmCode = 0;
    protected int levelCode = 0;

    public void init(CipherInfo cipherInfo, AlgorithmInfo algorithmInfo) {
        this.cipherInfo = cipherInfo;
        this.algorithmInfo = algorithmInfo;
        this.algorithmCode = cipherInfo.algorithm;
        this.levelCode = cipherInfo.level;
    }
}