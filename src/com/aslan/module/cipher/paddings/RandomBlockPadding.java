package com.aslan.module.cipher.paddings;

import com.aslan.config.Lookup;
import com.aslan.module.cipher.Padding;
import com.aslan.module.random.Random;
import com.aslan.module.random.rds.FastSecureRandom;
import com.aslan.module.utils.Utils;

public class RandomBlockPadding implements Padding {
    Random random = null;

    public RandomBlockPadding(Random random) {
        if (random == null) {
            random = new FastSecureRandom(Lookup.seedFactory(), null);
        }
        this.random = random;
    }

    public int compute(int N) {

        return (int) Utils.log2(N);
    }

    public void padding(byte[] v, int offset, int count) {

        random.next(v, offset, count);
    }
}