package com.aslan.module.cipher.algorithms;

import com.aslan.module.cipher.Algorithm;
import com.aslan.module.cipher.AlgorithmFactory;
import com.aslan.module.cipher.CipherInfo;

import java.util.SortedMap;
import java.util.TreeMap;

public abstract class VersionAlgorithmFactory implements AlgorithmFactory {
    protected SortedMap<Integer, Class<? extends Algorithm>> map = new TreeMap<>();

    public Algorithm make(CipherInfo option) throws Exception {
        int version = option.algorithmV;
        Class<? extends Algorithm> cl = form(version);
        if (cl == null) {
            throw new Error("不支持的加密算法版本({@name})：{1}");
        }
        Algorithm algorithm = cl.newInstance();
        option.algorithmV = algorithm.version();
        return algorithm;
    }

    protected Class<? extends Algorithm> form(int version) {
        if (version == 0) version = map.firstKey();
        return map.get(version);
    }
}