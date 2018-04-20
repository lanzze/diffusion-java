package com.aslan.module.random.rds;

import com.aslan.module.random.RandomEngine;
import com.aslan.module.random.SeedFactory;

public class FastSecureRandom extends SecureRandom {
    static int UPDATE_INTERVAL = 1024;
    int count = 0;

    public FastSecureRandom(SeedFactory factory, RandomEngine engine) {
        super(factory, engine);
        this.update();
    }

    public void seed(byte[] seed, int offset, int length) {
        engine.seed(seed, offset, length);
    }

    public void next(byte[] accept, int offset, int length) {
        if (this.count++ > FastSecureRandom.UPDATE_INTERVAL) {
            this.count = 0;
            this.update();
        }
        this.engine.next(accept, offset, length);
    }

    public byte rand() {
        return engine.rand();
    }

    protected void update() {
        factory.pull(this.seeds, 0, this.seeds.length);
        engine.seed(this.seeds, 0, this.seeds.length);
    }
}