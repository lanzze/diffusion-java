package com.aslan.module.cipher.keys.diligent;

import com.aslan.module.cipher.keys.VersionKeyFactory;

public class DiligentStreamKeyFactory extends VersionKeyFactory {

    {
        map.put(1, DiligentStreamKey1.class);
    }

}
