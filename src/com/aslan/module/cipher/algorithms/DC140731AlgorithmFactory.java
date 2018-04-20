package com.aslan.module.cipher.algorithms;

import com.aslan.module.cipher.Algorithm;

public class DC140731AlgorithmFactory extends AlgorithmFactoryProxy {

    {
        map.put(2, DC140713Algorithm_V2.class);
        map.put(1, DC140713Algorithm_V1.class);
    }


    @Override
    protected Class<? extends Algorithm> form(int version) {
        if (version == 0) version = map.firstKey();
        return map.get(version);
    }
}
