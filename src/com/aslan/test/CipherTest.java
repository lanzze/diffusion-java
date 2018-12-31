package com.aslan.test;

import com.aslan.module.cipher.*;
import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.algorithms.Box;
import com.aslan.module.cipher.algorithms.dc140731.DC140713Algorithm3;
import com.aslan.module.cipher.keys.diffusion.DiffusionKey2;
import com.aslan.module.core.Sbox;
import com.aslan.module.utils.Utils;
import sts.Sts;
import sts.analyze.*;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.*;

public class CipherTest {
    static byte[] V = {
            (byte) 0x01, (byte) 0x23, (byte) 0x45, (byte) 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef,
            (byte) 0xfe, (byte) 0xdc, (byte) 0xba, (byte) 0x98, (byte) 0x76, (byte) 0x54, (byte) 0x32, (byte) 0x10,
            (byte) 0x0f, (byte) 0x1e, (byte) 0x2d, (byte) 0x3c, (byte) 0x4b, (byte) 0x5a, (byte) 0x69, (byte) 0x78,
            (byte) 0x87, (byte) 0x96, (byte) 0xa5, (byte) 0xb4, (byte) 0xc3, (byte) 0xd2, (byte) 0xe1, (byte) 0xf0,
    };
    //    static byte[] V = {
//            (byte) 0xf0, (byte) 0xe1, (byte) 0xd2, (byte) 0xc3, (byte) 0xb4, (byte) 0xa5, (byte) 0x96, (byte) 0x87,
//            (byte) 0x78, (byte) 0x69, (byte) 0x5a, (byte) 0x4b, (byte) 0x3c, (byte) 0x2d, (byte) 0x1e, (byte) 0x0f,
//            (byte) 0x0f, (byte) 0x1e, (byte) 0x2d, (byte) 0x3c, (byte) 0x4b, (byte) 0x5a, (byte) 0x69, (byte) 0x78,
//            (byte) 0x87, (byte) 0x96, (byte) 0xa5, (byte) 0xb4, (byte) 0xc3, (byte) 0xd2, (byte) 0xe1, (byte) 0xf0,
//    };
    static int count = 1000000;
    static byte[] seed1 = Box.V1.ENC_BOX;
    static byte[] seed2 = com.aslan.module.cipher.keys.Box.V1.S1;
    static byte[] seed3 = com.aslan.module.cipher.keys.Box.V1.S2;
    static byte[] rands = new byte[32768];

    static Random r1 = new SecureRandom(seed1);
    static Random r2 = new SecureRandom(seed2);
    static Random r3 = new SecureRandom(seed3);
    static Random rr = new SecureRandom();

    static int g = 0;

    static {
        r3.nextBytes(rands);
        System.loadLibrary("libdfft");
    }


    public static void main(String[] args) throws Exception {
//        fixed();
        //        fixedWithNoKeyUpdate_a3();
        //        stream_lazy();
        //        randomTest_streamLazy();
//        sbox_test_change();
        //        sbox_test();
////        fixed();
//        while (true) {
//            key();
//            g++;
//        }
        sample(com.aslan.module.cipher.keys.Box.V2.N4_2);

//        rand(new byte[256], 256);
    }

    public static void key() throws Exception {
        int N = 32;
        AlgorithmInfo algorithmInfo = Utils.computeAlgorithmInfo(N, null);
        CipherInfo cipherInfo = new CipherInfo();
        cipherInfo.algorithm = Consts.ALGORITHMS.DC140713;
        cipherInfo.algorithmV = 3;
        cipherInfo.key = Consts.KEYS.FIXED;
        cipherInfo.keyV = 2;
//        cipherInfo.keyData = initKey2(algorithmInfo.R, algorithmInfo.N);

//        Key key = KeyBuilder.make(cipherInfo);
        Algorithm algorithm = AlgorithmBuilder.make(cipherInfo);
        algorithm.init(algorithmInfo);
        byte[] box = findBox2((DC140713Algorithm3) algorithm, N, algorithmInfo.R);
        sample(box);
        //one enc
//        algorithmInfo.R = 5;
//        algorithmInfo.N = 8;
//        byte[] M = new byte[N * 10];
//        byte[] K = new byte[N * algorithmInfo.R];
//        for (int i = 0; i < 10; i++) {
//            algorithm.enc(M, i * 32, K);
//            Utils.print(M, "\n");
//        }

//        Result[] results = Sts.from(M, N);
//        Sts.print(results);

    }

    public static void fixed() throws Exception {
        int N = 32;
        AlgorithmInfo algorithmInfo = Utils.computeAlgorithmInfo(N, null);
        CipherInfo cipherInfo = new CipherInfo();
        cipherInfo.algorithm = Consts.ALGORITHMS.DC140713;
        cipherInfo.algorithmV = 3;
        cipherInfo.key = Consts.KEYS.FIXED;
        cipherInfo.keyV = 2;
        cipherInfo.keyData = initKey2(algorithmInfo.R, algorithmInfo.N);

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


    private static byte[] initKey2(int r, int n) {
        byte[] K = new byte[r * n];
        for (int i = 0; i < r; i++) {
            System.arraycopy(V, 0, K, i * n, n);
        }
        return K;
    }


    private static void fill(byte[] b, boolean rand) {
        for (int i = 0; i < b.length; i++) {
            b[i] = rand ? rands[i & 255] : (byte) i;
        }
    }

    private static void findBox(DiffusionKey2 k, int N) throws Exception {
        byte[] buf = new byte[N << 1];
        byte[] X = new byte[N];
        byte[] Y = new byte[N];
        double limit = 0.01;
        long count = 0;
        boolean validate = true;
        Result[][] finals = new Result[6][];
        while (true) {
            if (++count % 100 == 0) {
                System.out.println("Found times: " + count);
            }
            byte[] box = Sbox.trys();
            k.setBox(box);
            Arrays.fill(X, (byte) 0);
            Arrays.fill(Y, (byte) 0);
            k.diffusion(X, Y);
            System.arraycopy(X, 0, buf, 0, N);
            System.arraycopy(Y, 0, buf, N, N);
            Result[] results = Sts.from(buf, N << 1);
            if (validate && !Sts.over(results, limit)) continue;
            finals[0] = results;

            Arrays.fill(X, (byte) 1);
            Arrays.fill(Y, (byte) 1);
            k.diffusion(X, Y);
            System.arraycopy(X, 0, buf, 0, N);
            System.arraycopy(Y, 0, buf, N, N);
            results = Sts.from(buf, N << 1);
            if (validate && !Sts.over(results, limit)) continue;
            finals[1] = results;

            fill(X, false);
            fill(Y, false);
            k.diffusion(X, Y);
            System.arraycopy(X, 0, buf, 0, N);
            System.arraycopy(Y, 0, buf, N, N);
            results = Sts.from(buf, N << 1);
            if (validate && !Sts.over(results, limit)) continue;
            finals[2] = results;

            fill(X, true);
            fill(Y, true);
            k.diffusion(X, Y);
            System.arraycopy(X, 0, buf, 0, N);
            System.arraycopy(Y, 0, buf, N, N);
            results = Sts.from(buf, N << 1);
            if (validate && !Sts.over(results, limit)) continue;
            finals[3] = results;

            rr.nextBytes(X);
            rr.nextBytes(Y);
            k.diffusion(X, Y);
            System.arraycopy(X, 0, buf, 0, N);
            System.arraycopy(Y, 0, buf, N, N);
            results = Sts.from(buf, N << 1);
            if (validate && !Sts.over(results, limit)) continue;
            finals[4] = results;

            rr.nextBytes(X);
            rr.nextBytes(Y);
            k.diffusion(X, Y);
            System.arraycopy(X, 0, buf, 0, N);
            System.arraycopy(Y, 0, buf, N, N);
            results = Sts.from(buf, N << 1);
            if (validate && !Sts.over(results, limit)) continue;
            finals[5] = results;


            Files.write(new File("F:\\temp\\box.dat").toPath(), box);
            System.out.println("found box: ");
            Sbox.print(box);
            Sbox.print2(Sbox.diff(box));
            for (Result[] e : finals) {
                System.out.println("--------------------------------------------------------------------------------------");
                Sts.print(e);
            }
            break;
        }

    }

    private static byte[] findBox2(DC140713Algorithm3 a, int N, int R) throws Exception {
        byte[] M = new byte[N];
        byte[] K = new byte[N * R];
        byte[] BOX = null;
        int counter = 0;
        int total = 0;
        int last = 0;
        while (counter++ < 1000) {
//            if (++counter % 100 == 0) {
//                System.out.println("Found times: " + counter);
//            }
            byte[] box = Sbox.trys();
            a.setBox(box);
            last = 0;
            Arrays.fill(M, (byte) 0);
            a.enc(M, 0, K);
            int count = test(M);
            if (count < 500) continue;
            last += count;


            Arrays.fill(M, (byte) 11);
            a.enc(M, 0, K);
            count = test(M);
            if (count < 500) continue;
            last += count;


            fill(M, false);
            a.enc(M, 0, K);
            count = test(M);
            if (count < 500) continue;
            last += count;


            r1.nextBytes(M);
            a.enc(M, 0, K);
            count = test(M);
            if (count < 500) continue;
            last += count;


            r2.nextBytes(M);
            a.enc(M, 0, K);
            count = test(M);
            if (count < 500) continue;
            last += count;

            rand(M, N);
            a.enc(M, 0, K);
            count = test(M);
            if (count < 500) continue;
            last += count;

            if (last > total) {
                total = last;
                BOX = box.clone();
            }
        }
        Files.write(new File(String.format("F:\\temp\\box\\box-[%3d][%5d].dat", g, total)).toPath(), BOX);
//        System.out.println("found completed, total is: " + total);
//        Sbox.print(BOX);
//        Sbox.print2(Sbox.diff(BOX));
        System.out.println(String.format("Random Excursion Count: %5d at %3d", total, g));
        return BOX;
    }

    static byte[] buf = new byte[1024 * 1024 * 8];

    private static int test(byte[] data) {
        int n = data.length;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < 8; j++) {
                buf[i * 8 + j] = (byte) ((data[i] >> (7 - j)) & 0x01);
            }
        }
        int[] S_k = new int[n];
        int J = 0;                    /* DETERMINE CYCLES */
        S_k[0] = 2 * buf[0] - 1;
        for (int i = 1; i < n; i++) {
            S_k[i] = S_k[i - 1] + 2 * buf[i] - 1;
            if (S_k[i] == 0) {
                J++;
            }
        }
        return J;
    }

    private static boolean sample(byte[] box) throws Exception {
        int N = 256;
        AlgorithmInfo algorithmInfo = Utils.computeAlgorithmInfo(N, null);
        CipherInfo cipherInfo = new CipherInfo();
        cipherInfo.algorithm = Consts.ALGORITHMS.DC140713;
        cipherInfo.algorithmV = 3;
        cipherInfo.key = Consts.KEYS.FIXED;
        cipherInfo.keyV = 2;
//        cipherInfo.keyData = initKey2(algorithmInfo.R, algorithmInfo.N);

//        Key key = KeyBuilder.make(cipherInfo);
        DC140713Algorithm3 algorithm = (DC140713Algorithm3) AlgorithmBuilder.make(cipherInfo);
        algorithm.setBox(box);
        algorithm.init(algorithmInfo);


        List<Result[]> list = new ArrayList<>();
        List<Analyze> analyzes = new ArrayList<>();
        //@formatter:off
        analyzes.add(new Frequency());                  //0
        analyzes.add(new BlockFrequency());             //1
        analyzes.add(new Runs());                       //3
        analyzes.add(new LongestRun());                 //4
        analyzes.add(new Rank());                       //5
        analyzes.add(new DiscreteFourierTransform());   //6
        analyzes.add(new ApproximateEntropy());         //10
        analyzes.add(new LinearComplexity());           //14
        analyzes.add(new OverlappingTemplate());        //8
        analyzes.add(new Universal());                  //9
        analyzes.add(new CumulativeSum());              //2
        analyzes.add(new Serial());                     //13
        analyzes.add(new RandomExcursion());            //11
        analyzes.add(new RandomExcursionVariant());     //12
        analyzes.add(new NonOverlappingTemplate());     //7
        //@formatter:on

        int ROUND = 16;
        int NN = N * (N + 1) * ROUND;


        byte[] buf = new byte[NN];
        byte[] M = new byte[N];
        byte[] K = new byte[N * algorithmInfo.R];
        //0-1:0
        for (int j = 0, c = 0; j < ROUND; j++) {
            for (int i = -1; i < N; i++) {
                byte[] m = M.clone();
                if (i >= 0) m[i] += 0x1;
                algorithm.enc(m, 0, K);
                System.arraycopy(m, 0, buf, N * c++, N);
            }
            update(M, N);
        }
        list.add(analyze(analyzes, NN, buf));
        //0:0-1
        Arrays.fill(M, (byte) 0);
        Arrays.fill(K, (byte) 0);
        for (int j = 0, c = 0; j < ROUND; j++) {
            for (int i = -1; i < N; i++) {
                byte[] m = M.clone();
                byte[] k = K.clone();
                if (i >= 0) k[i] += 0x1;
                algorithm.enc(m, 0, k);
                System.arraycopy(m, 0, buf, N * c++, N);
            }
            update(K, N);
        }

        list.add(analyze(analyzes, NN, buf));

        //ff-1:0
        Arrays.fill(M, (byte) 0xf0);
        Arrays.fill(K, (byte) 0);
        for (int j = 0, c = 0; j < ROUND; j++) {
            for (int i = -1; i < N; i++) {
                byte[] m = M.clone();
                if (i >= 0) m[i] += 0x1;
                algorithm.enc(m, 0, K);
                System.arraycopy(m, 0, buf, N * c++, N);
            }
            update(M, N);
        }

        list.add(analyze(analyzes, NN, buf));
        //0:88-1
        Arrays.fill(M, (byte) 0);
        Arrays.fill(K, (byte) 0xf0);
        for (int j = 0, c = 0; j < ROUND; j++) {
            for (int i = -1; i < N; i++) {
                byte[] m = M.clone();
                byte[] k = K.clone();
                if (i >= 0) k[i] += 0x1;
                algorithm.enc(m, 0, k);
                System.arraycopy(m, 0, buf, N * c++, N);
            }
            update(K, N);
        }
        list.add(analyze(analyzes, NN, buf));

        //0~n-1:0
        fill(M, false);
        Arrays.fill(K, (byte) 0);
        for (int j = 0, c = 0; j < ROUND; j++) {
            for (int i = -1; i < N; i++) {
                byte[] m = M.clone();
                if (i >= 0) m[i] += 0x1;
                algorithm.enc(m, 0, K);
                System.arraycopy(m, 0, buf, N * c++, N);
            }
            update(M, N);
        }

        list.add(analyze(analyzes, NN, buf));
        //0:0~n-1
        Arrays.fill(M, (byte) 0);
        Arrays.fill(K, (byte) 0);
        fill(K, false);
        for (int j = 0, c = 0; j < ROUND; j++) {
            for (int i = -1; i < N; i++) {
                byte[] m = M.clone();
                byte[] k = K.clone();
                if (i >= 0) k[i] += 0x1;
                algorithm.enc(m, 0, k);
                System.arraycopy(m, 0, buf, N * c++, N);
            }
            update(K, N);
        }
        list.add(analyze(analyzes, NN, buf));

        //rand-1:0
        rand(M, N);
        Arrays.fill(K, (byte) 0);
        for (int j = 0, c = 0; j < ROUND; j++) {
            for (int i = -1; i < N; i++) {
                byte[] m = M.clone();
                if (i >= 0) m[i] += 0x1;
                algorithm.enc(m, 0, K);
                System.arraycopy(m, 0, buf, N * c++, N);
            }
            update(M, N);
        }

        list.add(analyze(analyzes, NN, buf));
        //0:rand-1
        Arrays.fill(M, (byte) 0);
        Arrays.fill(K, (byte) 0);
        rand(K, N);
        for (int j = 0, c = 0; j < ROUND; j++) {
            for (int i = -1; i < N; i++) {
                byte[] m = M.clone();
                byte[] k = K.clone();
                if (i >= 0) k[i] += 0x1;
                algorithm.enc(m, 0, k);
                System.arraycopy(m, 0, buf, N * c++, N);
            }
            update(K, N);
        }

        list.add(analyze(analyzes, NN, buf));

        NN = N * N * 16;
        Utils.computeAlgorithmInfo(NN, algorithmInfo);
        byte[] MM = new byte[NN];
        byte[] KK = new byte[NN * algorithmInfo.R];
        algorithm.init(algorithmInfo);
        algorithm.enc(MM, 0, KK);
        list.add(analyze(analyzes, NN, MM));

        for (int i = 0; i < 9; i++) {
            for (Result result : list.get(i)) {
                if (result == null) return false;
            }
        }

        List<StringBuilder> lines = new ArrayList<>();

        int f = 0;
        for (Analyze analyze : analyzes) {
            String name = analyze.getClass().getSimpleName();
            Result[] results = list.get(0);
            if (results[f] != null && results[f].items != null) {
                for (int i = 0; i < results[f].items.length; i++) {
                    StringBuilder line = new StringBuilder(String.format("%30s %10s\t\t", name, results[f].items[i].name));
                    for (int j = 0; j < 9; j++) {
                        results = list.get(j);
                        line.append(String.format("%6f\t", results[f] != null ? results[f].items != null ? results[f].items[i].value : 0 : 0));
                    }
                    lines.add(line.append("\n"));
                }
            } else {
                StringBuilder line = new StringBuilder(String.format("%30s %10s\t\t", name, "-"));
                for (int i = 0; i < 9; i++) {
                    results = list.get(i);
                    line.append(String.format("%6f\t", results[f] != null ? results[f].value : 0));
                }
                lines.add(line.append("\n"));
            }
            f++;
        }
        boolean valid = true;
        out:
        for (int i = 0; i < 9; i++) {
            for (Result result : list.get(i)) {
                if (result == null) {
                    valid = false;
                    break out;
                }
            }
        }
        System.out.println(valid ? "valid" : "invalid");
        if (valid || true) {
            System.out.println("found one at: " + g);
            String path = String.format("F:\\temp\\sts\\all-%d.txt", g);
            Files.write(new File(path).toPath(), Arrays.toString(lines.toArray()).getBytes());
        }
        return true;
    }

    private static Result[] analyze(List<Analyze> analyzes, int n, byte[] data) {
        Result[] results = new Result[analyzes.size()];
        byte[] bits = sts.utils.Utils.tobits(data);
        for (int i = 0; i < analyzes.size(); i++) {
            Analyze analyze = analyzes.get(i);
            try {
                results[i] = analyze.analyze(n, analyze.block(), bits);
            } catch (Exception e) {

            }
        }
        return results;
    }

    static int A = 9;
    static int M = 256;
    static int C = 127;

    private static void rand(byte[] B, int N) {
        B[0] = 1;
        for (int i = 1; i < N; i++) {
            B[i] = (byte) ((A * B[i - 1] + C) % M);
        }
        byte[] T = B.clone();
        Arrays.sort(T);
        for (int i = 0; i < N; i++) {
            if (Arrays.binarySearch(T, (byte) i) < 0) {
                throw new RuntimeException("Bad random");
            }
        }
    }

    private static void update(byte[] B, int N) {
        for (int i = 0; i < N; i++) {
            B[i] += 0x1;
        }
    }
}
