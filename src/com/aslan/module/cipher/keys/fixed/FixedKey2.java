package com.aslan.module.cipher.keys.fixed;

import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.keys.diffusion.DiffusionKey2;

public class FixedKey2 extends DiffusionKey2 {

    @Override
    public int identity() {
        return Consts.KEYS.FIXED;
    }

    @Override
    public int version() {
        return 2;
    }
}