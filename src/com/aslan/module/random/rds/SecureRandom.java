package com.aslan.module.random.rds;

import com.aslan.module.random.Random;
import com.aslan.module.random.RandomEngine;
import com.aslan.module.random.SeedFactory;
import com.aslan.module.random.engines.DARandomEngine;

public class SecureRandom implements Random {
    protected RandomEngine engine = null;
    protected SeedFactory factory = null;
    protected byte[] seeds = null;

    public SecureRandom(SeedFactory factory, RandomEngine engine) {
        if (!(factory instanceof SeedFactory)) {
            throw new Error("Seed factory is null");
        }
        factory.init();
        this.factory = factory;

        if (!(engine instanceof RandomEngine)) {
            engine = new DARandomEngine();
        }
        this.engine = engine;
        this.seeds = new byte[engine.need()];
    }

    public void seed(byte[] seed, int offset, int length) {
        engine.seed(seed, offset, length);
    }

    public void next(byte[] accept, int offset, int length) {
        this.factory.pull(this.seeds, 0, this.seeds.length);
        this.engine.seed(this.seeds, 0, this.seeds.length);
        this.engine.next(accept, offset, length);
    }

    public byte rand() {
        return engine.rand();
    }
}