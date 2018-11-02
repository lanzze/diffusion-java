package com.aslan.test;

import com.aslan.module.cipher.*;
import com.aslan.module.cipher.algorithms.Box;
import com.aslan.module.cipher.algorithms.dc140731.DC140713Algorithm1;
import com.aslan.module.cipher.algorithms.dc140731.DC140713Algorithm3;
import com.aslan.module.cipher.keys.diffusion.DiffusionKey2;
import com.aslan.module.utils.Utils;

import javax.naming.NameNotFoundException;
import javax.print.DocFlavor;
import javax.swing.plaf.metal.MetalIconFactory;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class CipherTest {
    static byte[] V = {0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef, (byte) 0xfe, (byte) 0xdc, (byte) 0xba, (byte) 0x98, 0x76, 0x54, 0x32, 0x10};
    static int count = 1000000;
    static byte[] seed1 = Box.V1.ENC_BOX;
    static byte[] seed2 = com.aslan.module.cipher.keys.Box.V1.S1;
    static byte[] seed3 = com.aslan.module.cipher.keys.Box.V1.S2;
    static byte[] rands = new byte[32768];

    static Random random = new SecureRandom(seed2);

    static {
        random.nextBytes(rands);
    }

    public static void main(String[] args) throws Exception {
        //        fixed();
        //        fixedWithNoKeyUpdate_a3();
        //        stream_lazy();
        //        randomTest_streamLazy();
        //        key();
        sbox_test_change();
        //        sbox_test();
    }

    public static void key() throws Exception {
        int N = 32;
        AlgorithmInfo algorithmInfo = Utils.computeAlgorithmInfo(N, null);
        CipherInfo cipherInfo = new CipherInfo();
        cipherInfo.algorithm = Consts.ALGORITHMS.DC140713;
        cipherInfo.algorithmV = 2;
        cipherInfo.key = Consts.KEYS.STREAM_DILIGENT;
        cipherInfo.keyV = 1;
        byte[] kd = new byte[N * algorithmInfo.R];
        cipherInfo.keyData = kd;
        //        Arrays.fill(kd, (byte) 1);
        Key key = KeyBuilder.make(cipherInfo);
        System.out.println(Arrays.toString(kd));
        System.out.println();
        key.init(cipherInfo, algorithmInfo);
    }

    public static void fixed() throws Exception {
        int N = 16;
        AlgorithmInfo algorithmInfo = Utils.computeAlgorithmInfo(N, null);
        CipherInfo cipherInfo = new CipherInfo();
        cipherInfo.algorithm = Consts.ALGORITHMS.DC140713;
        cipherInfo.algorithmV = 3;
        cipherInfo.key = Consts.KEYS.FIXED;
        cipherInfo.keyV = 2;
        // cipherInfo.keyData = initKey(algorithmInfo.R, algorithmInfo.N);

        Key key = KeyBuilder.make(cipherInfo);
        DC140713Algorithm3 algorithm = (DC140713Algorithm3) AlgorithmBuilder.make(cipherInfo);
        algorithm.init(algorithmInfo);

        //one enc
        key.init(cipherInfo, algorithmInfo);
        byte[] M = V.clone();
        algorithm.enc(M, 0, key.update());
        System.out.println("\nAfter enc 1:");
        Utils.print(M, "\n");

        algorithm.show = false;

        //one dec
        key.init(cipherInfo, algorithmInfo);
        byte[] C = M.clone();
        algorithm.dec(C, 0, key.update());
        System.out.println("\nAfter dec 1:");
        Utils.print(C, "\n");

        //1000000 enc
        key.init(cipherInfo, algorithmInfo);
        byte[] MM = V.clone();
        for (int i = 0; i < count; i++) {
            algorithm.enc(MM, 0, key.update());
        }
        System.out.println("\nAfter enc " + count + ": ");
        Utils.print(MM, "\n");

        //1000000 dec
        key.init(cipherInfo, algorithmInfo);
        byte[] CC = MM.clone();
        for (int i = 0; i < count; i++) {
            algorithm.dec(CC, 0, key.update());
        }
        System.out.println("\nAfter dec " + count + ": ");
        Utils.print(CC, "\n");
    }

    public static void stream_diligent() throws Exception {
        int N = 16;
        AlgorithmInfo algorithmInfo = Utils.computeAlgorithmInfo(N, null);
        CipherInfo cipherInfo = new CipherInfo();
        cipherInfo.algorithm = Consts.ALGORITHMS.DC140713;
        cipherInfo.algorithmV = 2;
        cipherInfo.key = Consts.KEYS.STREAM_DILIGENT;
        cipherInfo.keyV = 1;
        byte[] kd = new byte[N * algorithmInfo.R];
        cipherInfo.keyData = kd;
        Arrays.fill(kd, (byte) 1);
        Key key = KeyBuilder.make(cipherInfo);
        key.init(cipherInfo, algorithmInfo);
        Algorithm algorithm = AlgorithmBuilder.make(cipherInfo);
        algorithm.init(algorithmInfo);
        byte[] M = new byte[N];
        Arrays.fill(M, (byte) 1);
        algorithm.enc(M, 0, key.update());
        System.out.println("After dec:");

        key.init(cipherInfo, algorithmInfo);
        algorithm.init(algorithmInfo);
        algorithm.dec(M, 0, key.update());
        System.out.println("After dec:");
        System.out.println(Arrays.toString(M));
    }


    public static void stream_lazy() throws Exception {
        int N = 16384;
        AlgorithmInfo algorithmInfo = Utils.computeAlgorithmInfo(N, null);
        CipherInfo cipherInfo = new CipherInfo();
        cipherInfo.algorithm = Consts.ALGORITHMS.DC140713;
        cipherInfo.algorithmV = 2;
        cipherInfo.key = Consts.KEYS.STREAM_LAZY;
        cipherInfo.keyV = 2;
        byte[] kd = new byte[N * algorithmInfo.R];
        cipherInfo.keyData = kd;
        Arrays.fill(kd, (byte) 1);
        Key key = KeyBuilder.make(cipherInfo);
        key.init(cipherInfo, algorithmInfo);
        Algorithm algorithm = AlgorithmBuilder.make(cipherInfo);
        algorithm.init(algorithmInfo);
        byte[] M = new byte[N];
        Arrays.fill(M, (byte) 1);
        algorithm.enc(M, 0, key.update());
        System.out.println("After enc:");
        System.out.println(Arrays.toString(M));
        key.init(cipherInfo, algorithmInfo);
        algorithm.init(algorithmInfo);
        algorithm.dec(M, 0, key.update());
        System.out.println("After dec:");
        System.out.println(Arrays.toString(M));
    }

    private static void sbox_test() throws Exception {
        int N = 2048;
        AlgorithmInfo algorithmInfo = Utils.computeAlgorithmInfo(N, null);
        CipherInfo cipherInfo = new CipherInfo();
        cipherInfo.algorithm = Consts.ALGORITHMS.DC140713;
        cipherInfo.algorithmV = 3;
        cipherInfo.key = Consts.KEYS.STREAM_LAZY;
        cipherInfo.keyV = 2;

        Algorithm algorithm = AlgorithmBuilder.make(cipherInfo);
        algorithm.init(algorithmInfo);
        int size = 1024 * 4;
        byte[][] key = new byte[algorithmInfo.R][N];
        for (int i = 0; i < 1; i++) {
            FileOutputStream os = new FileOutputStream("F:\\temp\\box\\enc.0.dat");
            byte[] M = new byte[N];
            for (int j = 0; j < size; j++) {
                algorithm.enc(M, 0, key);
                os.write(M);
            }
            os.close();
        }

    }

    private static void sbox_test_change() throws Exception {
        int N = 64;
        AlgorithmInfo algorithmInfo = Utils.computeAlgorithmInfo(N, null);
        CipherInfo cipherInfo = new CipherInfo();
        cipherInfo.algorithm = Consts.ALGORITHMS.DC140713;
        cipherInfo.algorithmV = 3;
        cipherInfo.key = Consts.KEYS.STREAM_LAZY;
        cipherInfo.keyV = 2;

        Algorithm algorithm = AlgorithmBuilder.make(cipherInfo);
        algorithm.init(algorithmInfo);
        cipherInfo.keyData = new byte[0];
        // byte[][] data = initKey(algorithmInfo.R, N);
        byte[][] data = new byte[algorithmInfo.R][N];
        FileOutputStream os = new FileOutputStream("F:\\temp\\box\\a.cha.dat");
        byte[] M = new byte[N];
        DiffusionKey2 k = (DiffusionKey2) KeyBuilder.make(cipherInfo);
        k.init(cipherInfo, algorithmInfo);
        // Arrays.fill(M, (byte) 1);
        // fill(M);
        // M[1] = 1;
        byte[] X = new byte[N];
        byte[] Y = new byte[N];
        // fill(Y);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 256; i++) {
            // byte[] _M = M.clone();
            // _M[0] = (byte) i;
            // algorithm.enc(_M, 0, data);
            // os.write(_M);
            // Utils.print(_M, "\n");

            byte[] _X = X.clone();
            byte[] _Y = Y.clone();
            _X[0] = (byte) i;
            k.diffusion(_X, _Y);
            os.write(_X);
            os.write(_Y);
            // Utils.print(_X, " ");
            // Utils.print(_Y, "\n");

            // _M[0] = (byte) i;
            // algorithm.dec(_M, 0, data);
            // Utils.print(_M, "\n");

        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        // algorithm.show = true;
        // byte[] _M = M.clone();
        // _M[0] = (byte) 0x02;
        // algorithm.enc(_M, 0, key);
        // System.out.println();
        //
        // _M = M.clone();
        // _M[0] = (byte) 0x21;
        // algorithm.enc(_M, 0, key);
        // System.out.println();
        // os.close();
    }

    private static byte[][] initKey(int r, int n) {
        byte[][] K = new byte[r][n];
        for (int i = 0; i < r; i++) {
            System.arraycopy(V, 0, K[i], 0, V.length);
        }
        return K;
    }

    private static byte[][] clone(byte[][] src) {
        byte[][] clone = new byte[src.length][];
        for (int i = 0; i < src.length; i++) {
            clone[i] = src[i].clone();
        }
        return clone;
    }

    private static void fill(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            b[i] = rands[i & 255];
            // b[i] = (byte) i;
        }
    }
}
