package com.aslan.module.cipher.cips;

import com.aslan.module.cipher.*;
import com.aslan.module.cipher.paddings.RandomBlockPadding;

public abstract class AbstractStreamCipher implements StreamCipher {
    protected Padding padding = new RandomBlockPadding(null);
    protected Key key = null;
    protected Algorithm algorithm = null;
    protected AlgorithmInfo algorithmInfo = null;
    protected CipherInfo cipherInfo = null;

    protected int algorithmCode = 0;
    protected int keyCode = 0;

    public void init(CipherInfo cipherInfo, AlgorithmInfo algorithmInfo) {
        this.cipherInfo = cipherInfo;
        this.algorithmInfo = algorithmInfo;
        this.algorithmCode = cipherInfo.algorithm;
        this.keyCode = cipherInfo.key;
    }
}