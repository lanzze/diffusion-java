package com.aslan.module.cipher.infos.dc3_14;

import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.TextInfoHandlerFactory;
import com.aslan.module.cipher.infos.SeriesTextInfoHandlerFactory;


public class DC3_14TextInfoHandlerFactory extends SeriesTextInfoHandlerFactory {

    public DC3_14TextInfoHandlerFactory(TextInfoHandlerFactory next) {
        super(next);

    }

    @Override
    protected byte[] getHeader() {

        return Consts.INFO_HEAD.DC3_14;
    }

    @Override
    public int length() {
        return 32;
    }
}