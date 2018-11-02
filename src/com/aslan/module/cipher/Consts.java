package com.aslan.module.cipher;

                                                
public class Consts {
    //@formatter:off
    public static class ALGORITHMS {
        public static int DEFAULT               = 0;
        public static int DC140713              = 1;
        public static int DC314                 = 2;
    }


    public static class CIPHER_OPTION  {
        public static int ENCIPHER 		        = 1 << 0;
        public static int DECIPHER 		        = 1 << 1;
        public static int PADDING 		        = 1 << 2;
        public static int ENCRYPT_FILENAME 		= 1 << 3;
        public static int TEXT 			        = 1 << 29;
        public static int FILE 			        = 1 << 30;
    }

    public static class KEYS{
        public static int FIXED                 = 1;
        public static int STREAM_LAZY           = 2;
        public static int STREAM_DILIGENT       = 3;
    }

    public static class INFO_HEAD{
        public static byte[] DC3_14             ={'D', 'C', '3', '.', '1', '4'};
    }
}



