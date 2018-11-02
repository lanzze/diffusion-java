package com.aslan.module.cipher.keys;

import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.Key;
import com.aslan.module.cipher.KeyFactory;
import com.aslan.module.cipher.keys.diligent.DiligentStreamKeyFactory;
import com.aslan.module.cipher.keys.fixed.FixedKeyFactory;
import com.aslan.module.cipher.keys.lazy.LazyStreamKeyFactory;
import com.aslan.module.core.DiffusionException;

import java.util.SortedMap;
import java.util.TreeMap;

public class SeriesKeyFactory implements KeyFactory {

    SortedMap<Integer, KeyFactory> map = new TreeMap<>();

    {
        map.put(Consts.KEYS.FIXED, new FixedKeyFactory());
        map.put(Consts.KEYS.STREAM_DILIGENT, new DiligentStreamKeyFactory());
        map.put(Consts.KEYS.STREAM_LAZY, new LazyStreamKeyFactory());
    }

    public Key make(CipherInfo option) throws Exception {
        int key = option.key;
        if (key == 0) {
            option.key = key = map.firstKey();
        }
        KeyFactory factory = map.get(key);
        if (factory == null) {
            throw new DiffusionException("不支持的加密算法：" + key);
        }
        return factory.make(option);
    }
}
