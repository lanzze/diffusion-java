package com.aslan.module.cipher.keys.fixed;

import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.keys.diffusion.DiffusionC2;

public class DCA_C2_FixedKey extends DiffusionC2 {

    @Override
    public int identity() {
        return Consts.KEYS.FIXED;
    }

    @Override
    public int version() {
        return 31;
    }
}