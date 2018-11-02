package com.aslan.module.cipher.keys;

import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.cipher.Key;
import com.aslan.module.cipher.KeyFactory;
import com.aslan.module.core.DiffusionException;

import java.util.SortedMap;
import java.util.TreeMap;


public abstract class VersionKeyFactory implements KeyFactory {
    protected SortedMap<Integer, Class<? extends Key>> map = new TreeMap<>();

    public Key make(CipherInfo option) throws Exception {
        int version = option.keyV;
        Class<? extends Key> cl = form(version);
        if (cl == null) {
            throw new DiffusionException("不支持的密匙算法版本({@name})：{}");
        }
        Key key = cl.newInstance();
        option.keyV = key.version();
        return key;
    }

    protected Class<? extends Key> form(int version) {
        if (version == 0) version = map.firstKey();
        return map.get(version);
    }
}