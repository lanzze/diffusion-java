package com.aslan.module.cipher.keys.g3;

import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.keys.diffusion.DiffusionGS2;

public class SG2 extends DiffusionGS2 {

    @Override
    public int identity() {
        return Consts.KEYS.FIXED;
    }

    @Override
    public int version() {
        return 31;
    }
}