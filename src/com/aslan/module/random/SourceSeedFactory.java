package com.aslan.module.random;

import java.util.ArrayList;
import java.util.List;

public class SourceSeedFactory implements SeedFactory {
    static int UPDATE_TIME = 5 * 60 * 1000;
    static int BUFFER_SIZE = 1024 * 1024;
    List<SeedSource> sources = new ArrayList<>();
    byte[] seeds = null;
    int N = 0;
    int index = 0;
    int cap = 0;


    public void init() {
    }

    public void register(SeedSource source) {
        this.sources.add(source);
        this.sources.sort((a, b) ->
                (int) (a.reliability() - b.reliability())
        );
    }

    public int pull(byte[] accept, int offset, int length) {
        int size = sources.size(), remainder = length;
        if (size == 0) throw new RuntimeException("Seed not provide");
        while (remainder > 0) {
            for (int i = 0; i < size; i++) {
                SeedSource source = sources.get(i);
                int got = source.pull(accept, offset, remainder);
                remainder -= got;
                offset += got;
                if (remainder <= 0) break;
            }
        }
        return length;
    }
}