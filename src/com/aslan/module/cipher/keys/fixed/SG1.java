package com.aslan.module.cipher.keys.fixed;

import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.keys.diffusion.DiffusionGS1;

public class SG1 extends DiffusionGS1 {

    @Override
    public int identity() {
        return Consts.KEYS.FIXED;
    }

    @Override
    public int version() {
        return 11;
    }
}