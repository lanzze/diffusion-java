package com.aslan.config;

import com.aslan.module.cipher.Consts;

public class Config {
    //@formatter:off
    public static class CIPHER_LIMIT  {
        public static int MIN_GROUP_BIT     = 1024;
        public static int MAX_GROUP_BIT     = 524288;
        public static int MIN_KEY_LENGTH    = 30;
        public static int MAX_CYCLE         = 0xFF;
    }

    public static class CIPHER_DEFAULT  {
        public static int ALGORITHM         = Consts.ALGORITHMS.DC140713;
        public static int GROUP             = 2048;
        public static int KEY               = Consts.KEYS.STREAM_LAZY;
        public static int CYCLE             = 1;
        public static int KEY_TIMEOVER      = 1;
        public static String FORMAT         = "text";
    }
}
