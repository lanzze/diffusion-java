package com.aslan.module.cipher.keys.fixed;

import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.keys.diffusion.DiffusionC1;

public class DCA_C1_FixedKey extends DiffusionC1 {

    @Override
    public int identity() {
        return Consts.KEYS.FIXED;
    }

    @Override
    public int version() {
        return 11;
    }
}