package com.aslan.test;

import com.aslan.module.utils.Utils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * <p>
 * 应用扩散算法进行数据加密和解密示例（以两组为例，分别为X和Y）。
 * </p>
 *
 * @author Angel
 * @version 2.0
 */
public class CryptoDemo {
    /*
     * S盒子， 任意0~255之间的不重复随机整数。 S盒子是用于数据加密时函数所使用的数据，用以消除线性。 S盒子的随机性将会影响加密质量。
     */
    //@formatter:off
    public static final byte[] S = { 
        (byte)0xd9, (byte)0x25, (byte)0x48, (byte)0x5c, (byte)0x55, (byte)0xa0, (byte)0x3c, (byte)0x94, 
        (byte)0x83, (byte)0x36, (byte)0x11, (byte)0x74, (byte)0x69, (byte)0x2e, (byte)0x59, (byte)0x7b, 
        (byte)0xd1, (byte)0x23, (byte)0x70, (byte)0x75, (byte)0x3a, (byte)0xbf, (byte)0x8c, (byte)0xd8, 
        (byte)0x2f, (byte)0x34, (byte)0xb3, (byte)0x79, (byte)0xa4, (byte)0x27, (byte)0xa9, (byte)0x1d, 
        (byte)0xb0, (byte)0xc8, (byte)0x38, (byte)0x8f, (byte)0x49, (byte)0x8b, (byte)0x89, (byte)0x0f, 
        (byte)0x3b, (byte)0x92, (byte)0x33, (byte)0xcf, (byte)0xcd, (byte)0x1b, (byte)0x06, (byte)0xf0, 
        (byte)0xfb, (byte)0x82, (byte)0xbb, (byte)0xc6, (byte)0xf2, (byte)0x07, (byte)0x4e, (byte)0xe8, 
        (byte)0x77, (byte)0x6c, (byte)0x88, (byte)0x28, (byte)0xba, (byte)0xb9, (byte)0xe1, (byte)0xe9, 
        (byte)0xd7, (byte)0x4b, (byte)0x81, (byte)0xde, (byte)0x08, (byte)0x0d, (byte)0xfc, (byte)0x03, 
        (byte)0x87, (byte)0x56, (byte)0x14, (byte)0x0a, (byte)0x63, (byte)0x98, (byte)0x7a, (byte)0xf9, 
        (byte)0x2c, (byte)0x7e, (byte)0x13, (byte)0x7d, (byte)0x04, (byte)0x01, (byte)0x9e, (byte)0xbc, 
        (byte)0x41, (byte)0xdf, (byte)0x05, (byte)0x4f, (byte)0xfa, (byte)0x47, (byte)0x53, (byte)0xf8, 
        (byte)0x46, (byte)0x52, (byte)0x8e, (byte)0x4a, (byte)0x76, (byte)0x09, (byte)0x1e, (byte)0xd2, 
        (byte)0x2d, (byte)0x3e, (byte)0x61, (byte)0x5a, (byte)0x20, (byte)0x29, (byte)0xd0, (byte)0x16, 
        (byte)0xb4, (byte)0x67, (byte)0xbd, (byte)0x50, (byte)0x60, (byte)0x35, (byte)0x19, (byte)0xd5, 
        (byte)0xdd, (byte)0x8a, (byte)0xea, (byte)0xa5, (byte)0xac, (byte)0x1a, (byte)0x58, (byte)0x1f, 
        (byte)0x5d, (byte)0x40, (byte)0x6d, (byte)0x86, (byte)0xeb, (byte)0x42, (byte)0x02, (byte)0x00, 
        (byte)0xaa, (byte)0x9b, (byte)0xa1, (byte)0x7f, (byte)0xe4, (byte)0xb8, (byte)0xe6, (byte)0x21, 
        (byte)0x68, (byte)0x12, (byte)0xd6, (byte)0x4d, (byte)0x4c, (byte)0x62, (byte)0xc0, (byte)0x66, 
        (byte)0x0e, (byte)0x72, (byte)0x64, (byte)0xf6, (byte)0x80, (byte)0xb5, (byte)0xd4, (byte)0xf4, 
        (byte)0x84, (byte)0x9d, (byte)0x73, (byte)0x51, (byte)0xe3, (byte)0x26, (byte)0xf3, (byte)0x1c, 
        (byte)0x96, (byte)0xe2, (byte)0xcc, (byte)0x90, (byte)0xa3, (byte)0xc5, (byte)0xda, (byte)0xf7, 
        (byte)0xad, (byte)0x6b, (byte)0x44, (byte)0x31, (byte)0xdb, (byte)0xa7, (byte)0xd3, (byte)0xfd, 
        (byte)0xb2, (byte)0xcb, (byte)0xc7, (byte)0xf1, (byte)0x91, (byte)0x97, (byte)0xae, (byte)0xb6, 
        (byte)0x45, (byte)0xc4, (byte)0xec, (byte)0xc2, (byte)0xce, (byte)0x18, (byte)0x3d, (byte)0xbe, 
        (byte)0x7c, (byte)0xe5, (byte)0xb1, (byte)0xc9, (byte)0x0c, (byte)0x54, (byte)0x24, (byte)0x43, 
        (byte)0x30, (byte)0x2a, (byte)0xb7, (byte)0xca, (byte)0x6e, (byte)0xc3, (byte)0x5b, (byte)0x9f, 
        (byte)0x10, (byte)0xee, (byte)0x8d, (byte)0x95, (byte)0x32, (byte)0xc1, (byte)0x57, (byte)0x0b, 
        (byte)0xed, (byte)0x99, (byte)0x5f, (byte)0xdc, (byte)0xaf, (byte)0x17, (byte)0xef, (byte)0xff, 
        (byte)0x9c, (byte)0x9a, (byte)0x85, (byte)0x2b, (byte)0xab, (byte)0x5e, (byte)0xf5, (byte)0x6f, 
        (byte)0xa6, (byte)0x39, (byte)0xa8, (byte)0x6a, (byte)0x3f, (byte)0x93, (byte)0x65, (byte)0xe0, 
        (byte)0xfe, (byte)0x71, (byte)0x15, (byte)0x78, (byte)0x22, (byte)0xa2, (byte)0x37, (byte)0xe7, };
 
    /*
    * 逆S盒子，设v是一个0~255之间的整数，_S必须满足_S[S[v]] = v.
    */
    public static final byte[] _S = {
        (byte)0x1c, (byte)0xc2, (byte)0xb1, (byte)0x88, (byte)0x20, (byte)0x6a, (byte)0x03, (byte)0xc8, 
        (byte)0xba, (byte)0xa6, (byte)0xf9, (byte)0xa5, (byte)0x96, (byte)0x5a, (byte)0xe2, (byte)0xa3, 
        (byte)0x2b, (byte)0x3c, (byte)0xa9, (byte)0x75, (byte)0x87, (byte)0x5b, (byte)0x28, (byte)0x3d, 
        (byte)0xcd, (byte)0x61, (byte)0x69, (byte)0x09, (byte)0x68, (byte)0x21, (byte)0xd6, (byte)0x57, 
        (byte)0x85, (byte)0x0a, (byte)0x7d, (byte)0x2c, (byte)0x9c, (byte)0xfb, (byte)0x70, (byte)0x35, 
        (byte)0x72, (byte)0x9e, (byte)0x08, (byte)0x6c, (byte)0xfc, (byte)0x30, (byte)0x3e, (byte)0x64, 
        (byte)0xa0, (byte)0x4a, (byte)0x38, (byte)0x9a, (byte)0xf0, (byte)0x1d, (byte)0x3f, (byte)0x52, 
        (byte)0x0d, (byte)0xbd, (byte)0xbc, (byte)0xb2, (byte)0xd7, (byte)0xf2, (byte)0x47, (byte)0x95, 
        (byte)0x16, (byte)0x5d, (byte)0x43, (byte)0x55, (byte)0x41, (byte)0x2d, (byte)0xb3, (byte)0x3a, 
        (byte)0xa1, (byte)0x4b, (byte)0x53, (byte)0x39, (byte)0x2a, (byte)0xac, (byte)0x44, (byte)0xab, 
        (byte)0xee, (byte)0x90, (byte)0xe7, (byte)0x36, (byte)0x1e, (byte)0xf7, (byte)0x12, (byte)0xc0, 
        (byte)0x97, (byte)0x80, (byte)0x2e, (byte)0x34, (byte)0x63, (byte)0xf8, (byte)0xc3, (byte)0xd9, 
        (byte)0x77, (byte)0xbe, (byte)0x29, (byte)0x24, (byte)0x0c, (byte)0x49, (byte)0x0e, (byte)0x7f, 
        (byte)0xb7, (byte)0xbf, (byte)0xfa, (byte)0x04, (byte)0x42, (byte)0x60, (byte)0x59, (byte)0x66, 
        (byte)0xaf, (byte)0x3b, (byte)0xb4, (byte)0x26, (byte)0x1f, (byte)0x6e, (byte)0x1b, (byte)0x2f, 
        (byte)0xdf, (byte)0xcf, (byte)0xdc, (byte)0xb0, (byte)0xc6, (byte)0x37, (byte)0x78, (byte)0x67, 
        (byte)0x07, (byte)0xd5, (byte)0x06, (byte)0xc7, (byte)0xd4, (byte)0xda, (byte)0xae, (byte)0xb5, 
        (byte)0xc4, (byte)0xe5, (byte)0xcb, (byte)0x5f, (byte)0x4c, (byte)0xc5, (byte)0x18, (byte)0xa7, 
        (byte)0x58, (byte)0x8a, (byte)0x11, (byte)0xd2, (byte)0xca, (byte)0x7a, (byte)0xef, (byte)0x65, 
        (byte)0x45, (byte)0xf6, (byte)0xfd, (byte)0xad, (byte)0x27, (byte)0x9f, (byte)0xe6, (byte)0xff, 
        (byte)0xec, (byte)0x0f, (byte)0x7c, (byte)0x91, (byte)0x4e, (byte)0x81, (byte)0x25, (byte)0x9d, 
        (byte)0xbb, (byte)0xed, (byte)0x51, (byte)0x6b, (byte)0xd0, (byte)0xe8, (byte)0x8d, (byte)0x98, 
        (byte)0x50, (byte)0x33, (byte)0x5c, (byte)0xaa, (byte)0x99, (byte)0xf5, (byte)0x89, (byte)0x7e, 
        (byte)0xa2, (byte)0x71, (byte)0x94, (byte)0xa8, (byte)0x86, (byte)0x46, (byte)0xe9, (byte)0x74, 
        (byte)0x01, (byte)0xd8, (byte)0x05, (byte)0x4f, (byte)0x32, (byte)0x40, (byte)0xe0, (byte)0xdd, 
        (byte)0x82, (byte)0xa4, (byte)0xe3, (byte)0xc1, (byte)0x14, (byte)0x13, (byte)0xb6, (byte)0xdb, 
        (byte)0xf3, (byte)0x23, (byte)0xe1, (byte)0xde, (byte)0x4d, (byte)0x84, (byte)0xc9, (byte)0x5e, 
        (byte)0xfe, (byte)0x8e, (byte)0xeb, (byte)0x56, (byte)0x83, (byte)0x00, (byte)0x6d, (byte)0x62, 
        (byte)0xf4, (byte)0xea, (byte)0x15, (byte)0xcc, (byte)0x1a, (byte)0x76, (byte)0x17, (byte)0xf1, 
        (byte)0x10, (byte)0x8c, (byte)0x73, (byte)0x31, (byte)0xb9, (byte)0x02, (byte)0x54, (byte)0x6f, 
        (byte)0x92, (byte)0x79, (byte)0x19, (byte)0x22, (byte)0x8b, (byte)0x93, (byte)0xe4, (byte)0xb8, 
        (byte)0x7b, (byte)0x9b, (byte)0xce, (byte)0x8f, (byte)0x48, (byte)0xd3, (byte)0xd1, (byte)0x0b, };
 
    static long LONG_TEN = 100000000000000L;
     
    int E = 2;        // 额外的循环数量E
    int N;            // 分组长度N
    int P;            // 基于长度N的完美长度。
    int round;        // 用于加密的轮数（对应扩散阶段的数量）。
    byte[][] KX;    // 用于加密的子密匙（X）， 每一轮的密匙都不相同。
    byte[][] KY;    // 用于加密的子密匙（Y）， 每一轮的密匙都不相同。
    // @formatter:on

    public CryptoDemo(int keySize) { // keySize:byte
        N = keySize;
        int G = (int) Math.ceil(Math.log(N) / Math.log(2)); // 计算以2为底N的对数,并向上取整
        P = 1 << G; // P=2的G次方
        round = G + E;
        KX = new byte[round][];
        KY = new byte[round][];
        System.out.println("N=" + 2 * N + ",H=" + N + ",R=" + round + ",P=" + P);
    }

    /**
     * 生成子密匙， 通过扩散算法使得原密匙的每一个密匙都影响其它任何一个密匙。
     * <p>
     * 每个周期完成后，将本周的输出作为下一个周期的输入再计算子密匙。共需round个周期。
     * </p>
     *
     * @param kx 原始密匙X
     * @param ky 原始密匙Y
     */
    public void init(byte[] kx, byte[] ky) {
        for (int i = 0; i < round; i++) {
            init(kx, ky, i);
        }
    }

    Function FKx = new XKey(); // 用于生成子密匙的函数X
    Function FKy = new YKey(); // 用于生成子密匙的函数Y

    private void init(byte[] kx, byte[] ky, int index) {
        int l = 0;
        for (int i = 1; i <= round; i++) {
            for (int r = 0; r < N; r++) {
                ky[r] = (byte) (FKy.call(kx[l], ky[r]) & 255);
                kx[l] = (byte) (FKx.call(kx[l], ky[r]) & 255);
                l = (++l) % N;
            }
            l = P >> i;
        }
        KX[index] = kx.clone();
        KY[index] = ky.clone();
    }

    /**
     * 用于生成子密匙的函数X实现。
     *
     * @author Angel
     */
    class XKey implements Function {

        int A = 11;
        int B = 21;
        int C = 31;

        @Override
        public int call(int x, int y, int... values) {
            return A * x * x + B * y + C;
        }
    }

    /**
     * 用于生成子密匙的函数Y实现。
     *
     * @author Angel
     */
    class YKey implements Function {
        long C = 0x6180339887L;

        @Override
        public int call(int x, int y, int... values) {
            double a = Math.sin(x);
            double b = Math.cos(y);
            long v = (long) ((a + b) * LONG_TEN + C);
            return (int) (v & 0xffffffff);
        }
    }

    // ----------------------------------------------------------------------------

    Function Ex = new XEncrypt(); // 用于加密进行扩散运算的函数X
    Function Ey = new YEncrypt(); // 用于加密进行扩散运算的函数Y

    /**
     * 加密明文。次方法只加密一组数据，X,Y的长度为N。
     *
     * @param X 明文X
     * @param Y 明文Y
     */
    public void encrypt(byte[] X, byte[] Y) {
        int x = 0;
        for (int i = 1, k = 0; i <= round; i++, k++) {
            for (int y = 0; y < N; y++) {
                Y[y] = (byte) Ey.call(X[x], Y[y], KY[k][y]);
                X[x] = (byte) Ex.call(X[x], Y[y], KX[k][y]); // KX[k][x]
                // 不建议使用%运算
                // 当N&(N-1)=0时，可使用&运算，即N=2的j次方（j>0,且j为整数）
                x = (++x) % N; // x = (++x) & (N-1);
            }
            x = P >> i;
        }
    }

    /**
     * 用于加密算法的函数X实现。
     *
     * @author Angel
     */
    class XEncrypt implements Function {
        /**
         * 由于java中byte的值域为[-128,127],因此需要将运算后的值加128以改变值域为[0,255]（余同）。
         *
         * <p>
         * 此处为行方便，特将key以其他参数的第0位传入(余同)。
         * </p>
         */
        @Override
        public int call(int left, int right, int... values) {
            // rands[0] is key
            return S[(left ^ values[0]) & 0xFF] ^ right;
        }
    }

    /**
     * 用于加密算法的函数Y实现。
     *
     * @author Angel
     */
    class YEncrypt implements Function {
        @Override
        public int call(int left, int right, int... values) {
            // rands[0] is key
            return S[(right ^ values[0]) & 0xFF] ^ left;
        }
    }

    // ------------------------------------------------------------------------
    Function Dx = new XDecrypt(); // 用于解密进行扩散运算的函数X
    Function Dy = new YDecrypt(); // 用于解密进行扩散运算的函数Y

    /**
     * 解密密文，次方法只解密一组数据，X,Y的长度为N。
     *
     * @param X 密文X，对应加密后所输出的X
     * @param Y 密文Y，对应加密后所输出的Y
     */
    public void decrypt(byte[] X, byte[] Y) {
        int x = 0;
        int k = round - 1;
        for (int i = 0; i < round; i++, k--) {
            for (int y = 0; y < N; y++) {
                X[x] = (byte) Dx.call(X[x], Y[y], KX[k][y]); // KX[k][x]
                Y[y] = (byte) Dy.call(X[x], Y[y], KY[k][y]);
                x = (++x) % N; // x=(++x)&(N-1); and N&(N-1)=0
            }
            x = (1 << i) % P; // x=(1<<i)&(P-1); and P&(P-1)=0
        }
    }

    /**
     * 用于解密算法的函数X实现。
     *
     * @author Angel
     */
    class XDecrypt implements Function {
        @Override
        public int call(int left, int right, int... values) {
            // rands[0] is key
            return _S[(left ^ right) & 0xFF] ^ values[0];
        }
    }

    /**
     * 用于解密算法的函数Y实现。
     *
     * @author Angel
     */
    class YDecrypt implements Function {
        @Override
        public int call(int left, int right, int... values) {
            // rands[0] is key
            return _S[(right ^ left) & 0xFF] ^ values[0];
        }
    }

    // ---------------------------------------------------------------------------
    static interface Function {
        int call(int left, int right, int... values);
    }

    static void print(byte[] array) {
        for (byte b : array) {
            String s = Integer.toHexString(b & 0xff);
            if (s.length() == 1) {
                s = '0' + s;
            }
            System.out.print(s + ", ");
        }
    }
    // --------------------------------------------------------------------------

    /**
     * 微变加密测试， 每次加密只需改变原输入的一个元素（可以是密匙或者明文）。
     * 不建议使用弱密匙，当所有左密匙的元素相等且所有右密匙的元素都相等时为弱密匙，即：
     * <p>
     * LK[0]=LK[1]=LK[2]=...=LK[n]; RK[0]=RK[1]=RK[2]=...=RK[n];
     * </P>
     */
    public static void microChangeTest() throws Exception {
        AESEngine aes = new AESEngine();
        byte[] data = new byte[16];
        KeyParameter key = new KeyParameter(data);

        aes.init(true, key);
        byte[] M = new byte[16];
        Arrays.fill(M, (byte) 1);
        byte[] C = new byte[16];
        OutputStream os = new FileOutputStream("F:\\temp\\box\\aes.dat");
        for (int i = 0; i < 1; i++) {
            byte[] _M = M.clone();
            aes.processBlock(_M, 0, C, 0);
            os.write(C);
            M[0]++;
        }
    }

    public static void main(String[] args) throws Exception {
        microChangeTest();
    }

}