package com.aslan.module.cipher;

import com.aslan.module.cipher.keys.FixedKeyFactory;
import com.aslan.module.cipher.keys.StreamKeyFactory;
import com.aslan.module.core.DiffusionException;

import java.util.SortedMap;
import java.util.TreeMap;

public class KeyBuilder {
    static SortedMap<Integer, KeyFactory> map = new TreeMap<>();

    static {
        map.put(Consts.LEVELS.FIXED, new FixedKeyFactory());
        map.put(Consts.LEVELS.STREAM, new StreamKeyFactory());
    }

    static Key make(CipherInfo option) throws Exception {
        int level = option.level;
        if (level == 0) {
            option.level = level = map.firstKey();
        }
        KeyFactory factory = map.get(level);
        if (factory == null) {
            throw new DiffusionException("不支持的加密算法：" + level);
        }
        return factory.make(option);
    }
}
