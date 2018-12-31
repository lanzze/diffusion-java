package com.aslan.module.cipher.algorithms.dc140731;

import com.aslan.module.cipher.algorithms.VersionAlgorithmFactory;

public class DC140713AlgorithmFactory extends VersionAlgorithmFactory {

    {
        map.put(4, DC140713Algorithm4.class);
        map.put(3, DC140713Algorithm3.class);
        map.put(2, DC140713Algorithm2.class);
        map.put(1, DC140713Algorithm1.class);
    }
}
