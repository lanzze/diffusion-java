package com.aslan.module.cipher.algorithms;

import com.aslan.module.cipher.Algorithm;
import com.aslan.module.cipher.AlgorithmFactory;
import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.algorithms.dc140731.DC140713AlgorithmFactory;
import com.aslan.module.core.DiffusionException;

import java.util.SortedMap;
import java.util.TreeMap;

public class SeriesAlgorithmFactory implements AlgorithmFactory {

    SortedMap<Integer, AlgorithmFactory> map = new TreeMap<>();

    {
        map.put(Consts.ALGORITHMS.DC140713, new DC140713AlgorithmFactory());
    }

    public Algorithm make(CipherInfo option) throws Exception {
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
