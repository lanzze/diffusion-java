package com.aslan.module.cipher;

import com.aslan.module.cipher.algorithms.DC140731AlgorithmFactory;
import com.aslan.module.core.DiffusionException;

import java.util.SortedMap;
import java.util.TreeMap;

public class AlgorithmBuilder {

    static SortedMap<Integer, AlgorithmFactory> map = new TreeMap<>();

    static {
        map.put(Consts.ALGORITHMS.DC140713, new DC140731AlgorithmFactory());
    }

    static Algorithm make(CipherInfo option) throws Exception {
        int algorithm = option.algorithm;
        if (algorithm == 0) {
            option.algorithm = algorithm = map.firstKey();
        }
        AlgorithmFactory factory = map.get(algorithm);
        if (factory == null) {
            throw new DiffusionException("不支持的加密算法：" + algorithm);
        }
        return factory.make(option);
    }
}
