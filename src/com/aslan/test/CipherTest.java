package com.aslan.test;

import com.aslan.module.cipher.*;
import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.algorithms.Box;
import com.aslan.module.cipher.algorithms.dc140731.DC140713Algorithm3;
import com.aslan.module.cipher.keys.fixed.DCA_C1_FixedKey;
import com.aslan.module.core.Sbox;
import com.aslan.module.utils.Utils;
import sts.Sts;
import sts.analyze.*;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

public class CipherTest {
    static byte[] V = {
            (byte) 0x01, (byte) 0x23, (byte) 0x45, (byte) 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef,
            (byte) 0xfe, (byte) 0xdc, (byte) 0xba, (byte) 0x98, (byte) 0x76, (byte) 0x54, (byte) 0x32, (byte) 0x10,
            (byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77,
            (byte) 0x88, (byte) 0x99, (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff,
    };

    static byte[] seed1 = Box.V1.ENC_BOX;
    static byte[] seed2 = com.aslan.module.cipher.keys.Box.V1.S1;
    static byte[] seed3 = com.aslan.module.cipher.keys.Box.V1.S2;
    static byte[] rands = new byte[32768];

    static Random r1 = new SecureRandom(seed1);
    static Random r2 = new SecureRandom(seed2);
    static Random r3 = new SecureRandom(seed3);
    static Random r4 = new SecureRandom(Box.V2.ENC_BOX);
    static Random rr = new SecureRandom();

    static int g = 0, TOTAL = 0;

    static {
        r4.nextBytes(rands);
        System.loadLibrary("libdfft");
    }


    public static void main(String[] args) throws Exception {


//        writeFileTest();
//        fixed();

//        testSG2();

        sample(Box.V2.ENC_BOX);
        //        fixedWithNoKeyUpdate_a3();
        //        stream_lazy();
        //        randomTest_streamLazy();
//        sbox_test_change();
        //        sbox_test();
//        fixed();
//        while (true) {
//            key();
//            g++;
//        }
//        testbox();

//        for (int i = 3; i < 30; i++) {
//            int n = 1 << i;
//            long r = i + 1L;
//            long c = n * r;
//            System.out.println(String.format("n=%d,r=%d,n*r=%d,h=%f,hh=%f", n, r, c, c / 2.0, c / 4.0));
//        }


    }


    public static void key() throws Exception {
        int N = 1024 * 1024;
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
        if (sample(box)) {
            Files.write(new File(String.format("F:\\temp\\box\\box-[%3d][%5d].dat", g, TOTAL)).toPath(), box);
            System.out.println(String.format("Random Excursion Count: %5d at %3d", TOTAL, g));
        }
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

    public static void testSG2() throws Exception {
        int N = 32;
        AlgorithmInfo algorithmInfo = Utils.computeAlgorithmInfo(N, null);
        CipherInfo cipherInfo = new CipherInfo();
        cipherInfo.algorithm = Consts.ALGORITHMS.DC140713;
        cipherInfo.algorithmV = 3;
        cipherInfo.key = Consts.KEYS.FIXED;
        cipherInfo.keyV = 20;
        cipherInfo.keyData = V;


        System.out.println(String.format("Algorithm:(N=%2d, R=%2d)\n", algorithmInfo.N, algorithmInfo.R));

        Key key = KeyBuilder.make(cipherInfo);
        key.init(cipherInfo, algorithmInfo);

        Utils.print(key.update(), "\n", 32);

        DC140713Algorithm3 algorithm = (DC140713Algorithm3) AlgorithmBuilder.make(cipherInfo);
        algorithm.init(algorithmInfo);

        byte[] M = new byte[N];
        System.arraycopy(V, 0, M, 0, 32);
        algorithm.enc(M, 0, key.update());

        Utils.print(M, "\n", 32);

        algorithm.dec(M, 0, key.update());

        Utils.print(M, "\n", 32);

    }

    /**
     * 申请文档的加密测试
     *
     * @throws Exception
     */
    public static void fixed() throws Exception {
        int N = 32;
        AlgorithmInfo algorithmInfo = Utils.computeAlgorithmInfo(N, null);
        CipherInfo cipherInfo = new CipherInfo();
        cipherInfo.algorithm = Consts.ALGORITHMS.DC140713;
        cipherInfo.algorithmV = 3;
        cipherInfo.key = Consts.KEYS.FIXED;
        cipherInfo.keyV = 10;
        cipherInfo.keyData = V.clone();

        Key key = KeyBuilder.make(cipherInfo);
        DC140713Algorithm3 algorithm = (DC140713Algorithm3) AlgorithmBuilder.make(cipherInfo);
        algorithm.init(algorithmInfo);


        //one enc
        key.init(cipherInfo, algorithmInfo);
        byte[] M = V.clone();
        algorithm.enc(M, 0, key.update());
        System.out.println("\nAfter enc 1:");
        Utils.print(M, "\n");

////
//        one dec
//        key.init(cipherInfo, algorithmInfo);
        byte[] C = M.clone();
        algorithm.dec(C, 0, key.update());
        System.out.println("\nAfter dec 1:");
        Utils.print(C, "\n");
//
//        //1000000 enc
//        int max = 1024 * 1024 * 1024;
//        int count = max / N;
//        int count = 1000000;
//        key.init(cipherInfo, algorithmInfo);
//        long start = System.currentTimeMillis();
//        byte[] MM = V.clone();
//        for (int i = 0; i < count; i++) {
//            algorithm.enc(MM, 0, key.update());
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("\nAfter enc " + count + ": ");
//        System.out.println("\nUse time:" + (end - start));
//        Utils.print(MM, "\n");
//
//        //1000000 dec
////        key.init(cipherInfo, algorithmInfo);
//        byte[] CC = MM.clone();
//        for (int i = 0; i < count; i++) {
//            algorithm.dec(CC, 0, key.update());
//        }
//        System.out.println("\nAfter dec " + count + ": ");
//        Utils.print(CC, "\n");


    }

    public static void writeFileTest() throws Exception {
        int N = 1024;
        AlgorithmInfo algorithmInfo = Utils.computeAlgorithmInfo(N, null);
        CipherInfo cipherInfo = new CipherInfo();
        cipherInfo.algorithm = Consts.ALGORITHMS.DC140713;
        cipherInfo.algorithmV = 3;
        cipherInfo.key = Consts.KEYS.FIXED;
        cipherInfo.keyV = 10;
        cipherInfo.keyData = initKey2(algorithmInfo.R, algorithmInfo.N);

        Key key = KeyBuilder.make(cipherInfo);
        DC140713Algorithm3 algorithm = (DC140713Algorithm3) AlgorithmBuilder.make(cipherInfo);
        algorithm.init(algorithmInfo);


//        //one enc
        byte[] bytes = new byte[N * 100];
        FileOutputStream out = new FileOutputStream("A:\\sin.txt");
        key.init(cipherInfo, algorithmInfo);
        for (int i = 0; i < 100; i++) {
            algorithm.enc(bytes, i * N, key.update());
        }
        out.write(bytes);
        out.close();

        File file = new File("A:\\sin.txt");
        byte[] data = Files.readAllBytes(file.toPath());
        for (int i = 0; i < 100; i++) {
            algorithm.dec(data, i * N, key.update());
        }
        FileOutputStream out2 = new FileOutputStream("A:\\sot.txt");
        out2.write(data);
        out2.close();
    }

    private static byte[] initKey2(int r, int n) {
        byte[] K = new byte[r * n];
        for (int i = 0; i < r; i++) {
//            System.arraycopy(V, 0, K, i * n, n);
        }
        return K;
    }

    private static void fill(byte[] b, boolean rand) {
        for (int i = 0; i < b.length; i++) {
            b[i] = rand ? rands[i & 255] : (byte) i;
        }
    }

    private static void testbox() throws Exception {
        int N = 1024 * 1024;
        AlgorithmInfo algorithmInfo = Utils.computeAlgorithmInfo(N, null);
        CipherInfo cipherInfo = new CipherInfo();
        cipherInfo.algorithm = Consts.ALGORITHMS.DC140713;
        cipherInfo.algorithmV = 3;
        cipherInfo.key = Consts.KEYS.FIXED;
        cipherInfo.keyV = 2;
        Algorithm algorithm = AlgorithmBuilder.make(cipherInfo);
        algorithm.init(algorithmInfo);

        DC140713Algorithm3 dc3 = (DC140713Algorithm3) algorithm;
        File[] files = new File("F:\\temp\\box\\").listFiles();
        Map<File, List<Result[]>> map = new HashMap<>();

        Map<File, byte[]> boxs = new HashMap<>();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            boxs.put(file, Files.readAllBytes(file.toPath()));
            map.put(file, new ArrayList<>());
        }
        int inc = 0;
        while (map.size() > 1) {
            System.out.println("has : " + map.size());
            for (int i = 0; i < 500; i++) {
                Random random = new SecureRandom();
                byte[] M = new byte[N];
                byte[] K = new byte[N * algorithmInfo.R];
                random.nextBytes(M);
                random.nextBytes(K);
                for (File file : map.keySet()) {
                    byte[] m = M.clone();
                    dc3.setBox(boxs.get(file));
                    dc3.enc(m, 0, K.clone());
                    Result[] results = analyze(analyzes, N, m);
                    if (check(results)) {
                        map.get(file).add(results);
                    }
                }
            }
            File dir = new File(String.format("F:\\temp\\sts-%d\\", inc++));
            dir.mkdirs();
            map.forEach((file, result) -> {
                try {
                    write(result, String.format(dir.getAbsolutePath() + "\\sts-(%3s)-%s", result.size(), file.getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            size = 1000;
            map.values().forEach(e -> {
                if (e.size() < size) size = e.size();
            });
            Iterator<Map.Entry<File, List<Result[]>>> iterator = map.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<File, List<Result[]>> next = iterator.next();
                if (next.getValue().size() <= size + 1) {
                    iterator.remove();
                }
                next.getValue().clear();
            }
        }
    }

    static int size = 0;

    public static boolean check(Result[] results) {
        for (Result result : results) {
            if (result == null) return false;
            if (result.value == 0 && result.items == null) return false;
        }
        return true;
    }

    private static List<Result[]> testBox(DC140713Algorithm3 a, int N, int R, byte[] box) throws Exception {
        byte[] M = new byte[N], m;
        byte[] K = new byte[N * R];
        list.clear();
        a.setBox(box);
        Random r3 = new SecureRandom(Box.V2.ENC_BOX);

        m = M.clone();
        r3.nextBytes(m);
        a.enc(m, 0, K);
        list.add(analyze(analyzes, N, m));

        m = M.clone();
        r3.nextBytes(m);
        a.enc(m, 0, K);
        list.add(analyze(analyzes, N, m));

        m = M.clone();
        r3.nextBytes(m);
        a.enc(m, 0, K);
        list.add(analyze(analyzes, N, m));

        m = M.clone();
        r3.nextBytes(m);
        a.enc(m, 0, K);
        list.add(analyze(analyzes, N, m));


        m = M.clone();
        r3.nextBytes(m);
        a.enc(m, 0, K);
        list.add(analyze(analyzes, N, m));

        m = M.clone();
        r3.nextBytes(m);
        a.enc(m, 0, K);
        list.add(analyze(analyzes, N, m));

        m = M.clone();
        r3.nextBytes(m);
        a.enc(m, 0, K);
        list.add(analyze(analyzes, N, m));

        m = M.clone();
        r3.nextBytes(m);
        a.enc(m, 0, K);
        list.add(analyze(analyzes, N, m));

        m = M.clone();
        r3.nextBytes(m);
        a.enc(m, 0, K);
        list.add(analyze(analyzes, N, m));

        return list;
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
        TOTAL = total;
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

    static List<Result[]> list = new ArrayList<>();

    static List<Analyze> analyzes = new ArrayList<>();

    static {
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
    }


    private static boolean sample(byte[] box) throws Exception {
        int N = 256;
        AlgorithmInfo algorithmInfo = Utils.computeAlgorithmInfo(N, null);
        CipherInfo cipherInfo = new CipherInfo();
        cipherInfo.algorithm = Consts.ALGORITHMS.DC140713;
        cipherInfo.algorithmV = 3;
        cipherInfo.key = Consts.KEYS.FIXED;
        cipherInfo.keyV = 10;
        cipherInfo.keyData = initKey2(algorithmInfo.R, algorithmInfo.N);

//        Key key = KeyBuilder.make(cipherInfo);
        DC140713Algorithm3 algorithm = (DC140713Algorithm3) AlgorithmBuilder.make(cipherInfo);
//        algorithm.setBox(box);
        algorithm.init(algorithmInfo);

        list.clear();


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


        if (valid) {
            System.out.println("found one at: " + g);
            write(list, String.format("A:\\sts\\all-%d.txt", g));
        }
        return valid;
    }

    private static Result[] analyze(List<Analyze> analyzes, int n, byte[] data) {
        Result[] results = new Result[analyzes.size()];
        byte[] bits = sts.utils.Utils.tobits(data);
        for (int i = 0; i < analyzes.size(); i++) {
            Analyze analyze = analyzes.get(i);
            try {
                results[i] = analyze.analyze(n*8, analyze.block(), bits);
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

    private static void write(List<Result[]> list, String file) throws Exception {
        List<StringBuilder> lines = new ArrayList<>();
        int f = 0, size = list.size();
        Result[] template = list.get(0);
        for (Analyze analyze : analyzes) {
            String name = analyze.getClass().getSimpleName();
            if (template[f] != null && template[f].items != null) {
                for (int i = 0; i < template[f].items.length; i++) {
                    StringBuilder line = new StringBuilder(String.format("%30s, %10s,\t\t", name, template[f].items[i].name));
                    for (int j = 0; j < size; j++) {
                        Result[] results = list.get(j);
                        line.append(String.format("%6f,\t", results[f] != null ? results[f].items != null ? results[f].items[i].value : 0 : 0));
                    }
                    lines.add(line.append("\n"));
                }
            } else if (template[f] != null) {
                StringBuilder line = new StringBuilder(String.format("%30s, %10s,\t\t", name, "-"));
                for (int i = 0; i < size; i++) {
                    Result[] results = list.get(i);
                    line.append(String.format("%6f,\t", results[f] != null ? results[f].value : 0));
                }
                lines.add(line.append("\n"));
            } else {
                System.out.println("not pass:" + file);
                return;
            }
            f++;
        }
        Files.write(new File(file).toPath(), Arrays.toString(lines.toArray()).getBytes());
    }
}
