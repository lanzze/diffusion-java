package com.aslan.test;

import com.aslan.module.cipher.*;
import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.algorithms.dc140731.DC140713Algorithm3;
import com.aslan.module.cipher.keys.fixed.DCA_C1_FixedKey;
import com.aslan.module.utils.Utils;
import sts.Sts;
import sts.analyze.*;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomnessTest {

    static List<Analyze> analyzes = new ArrayList<>();

    static {
        System.loadLibrary("libdfft");
    }

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

    public static void main(String[] args) throws Exception {
        run();
    }

    public static void run() throws Exception {
        int N = 64;
        byte[][] messages = {
                new byte[N],   //0
                new byte[N],   //0xff
                new byte[N],   //0-N
                new byte[N],   //random
        };
        AlgorithmInfo algorithmInfo = Utils.computeAlgorithmInfo(N, null);
        CipherInfo cipherInfo = new CipherInfo();
        cipherInfo.algorithm = Consts.ALGORITHMS.DC140713;
        cipherInfo.algorithmV = 3;
        cipherInfo.key = Consts.KEYS.FIXED;
        cipherInfo.keyV = 10;
        cipherInfo.keyData = new byte[N];

        DC140713Algorithm3 algorithm = (DC140713Algorithm3) AlgorithmBuilder.make(cipherInfo);
        algorithm.init(algorithmInfo);


        for (int i = 0; i < N; i++) {
            messages[1][i] = (byte) 0xff;
        }
        for (int i = 0; i < N; i++) {
            messages[2][i] = (byte) i;
        }
        Random random = new SecureRandom();
        random.nextBytes(messages[3]);

        List<Result[]> list = new ArrayList<>();

        ByteBuffer buf = ByteBuffer.allocate(1024 * 1024);
        byte[] key = new byte[algorithmInfo.R * algorithmInfo.N];
        for (int s = 0; s < messages.length; s++) {
            buf.rewind();
            byte[] message = messages[s].clone();
            algorithm.enc(message, 0, key);
            buf.put(message);
            for (int i = 0; i < N; i++) {
                message = messages[s].clone();
                for (int j = 0; j < 0xfe; j++) {
                    message[i]++;
                    byte[] data = message.clone();
                    algorithm.enc(data, 0, key);
                    buf.put(data);
                }
            }
            int length = buf.position() * 8;
            byte[] data = new byte[buf.position()];
            System.arraycopy(buf.array(),0,data,0,buf.position());
            list.add(analyze(analyzes, length, data));
        }
        write(list, "A:\\randomness.txt");
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
