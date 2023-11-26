package com.aslan.test;

import com.aslan.module.cipher.AlgorithmInfo;
import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.Key;
import com.aslan.module.cipher.algorithms.Box;
import com.aslan.module.cipher.keys.fixed.DCA_C1_FixedKey;
import com.aslan.module.core.Sbox;
import com.aslan.module.utils.Utils;
import sts.Sts;
import sts.analyze.Item;
import sts.analyze.Result;

import java.io.File;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.Random;

public class KeyAlgorithmTest {


    public static void main(String[] args) throws Exception {

//        run();
//        byte[] $ = Sbox.map(Box.V2.ENC_BOX);
        Sbox.check(Box.V2.DEC_BOX, Box.V2.ENC_BOX);
//        Sbox.print2($);
//        byte[] S = Box.V2.ENC_BOX;
//        byte[] $ = Sbox.map(S);
//        int a = S[0x80] & 0xff;
//        int b = $[a] & 0xff;
//        System.out.println(Integer.toHexString(a));
//        System.out.println(Integer.toHexString(b));
//        Sbox.print2(Box.V2.ENC_BOX);

    }

    public static void run() throws Exception {
        int N = 64;
        AlgorithmInfo algorithm = Utils.computeAlgorithmInfo(N, null);
        CipherInfo cipher = new CipherInfo();
        DCA_C1_FixedKey key = new DCA_C1_FixedKey();
        cipher.key = Consts.KEYS.FIXED;
        cipher.keyV = 10;
        byte[] key_data = new byte[N];
        int L = algorithm.R * algorithm.N;
        FileOutputStream out1 = new FileOutputStream("A:\\a.txt");
        Random random = new SecureRandom();
//        random.nextBytes(key_data);

        byte[] buf = new byte[N * 256];


        for (int i = 0; i < 256; i++) {
            random.nextBytes(key_data);
//            byte[] key_data = new byte[N];
            cipher.keyData = key_data;
            key.init(cipher, algorithm);
//            byte[] data1 = key.produce1(algorithm.R);
//            out1.write(data1);
//            byte[] data2 = key.produce2(algorithm.R);
//            out2.write(data2);
//            key_data[i]=1;
            key_data[0]++;

        }
//        out1.close();
//        int length = L * N * 8;
//        File file1 = new File("A:\\a.txt");
//        Result[] results1 = Sts.from(file1, length);
//        File file2 = new File("A:\\b.txt");
//        Result[] results2 = Sts.from(file2, length);
//
//        int count1 = pass(results1);
//        int count2 = pass(results2);
//
//        System.out.println("algorithm1: " + count1);
//        System.out.println("algorithm2: " + count2);

//        Sts.print(results2);
    }


    public static int pass(Result[] results) {
        int count = 0;
        for (Result result : results) {
            if (result == null) {
                count++;
                continue;
            }
            Item[] items = result.items;
            if (items != null) {
                for (Item item : items) {
                    if (!item.success)
                        count++;
                }
            } else if (!result.success) {
                count++;
            }
        }
        return count;
    }
}
