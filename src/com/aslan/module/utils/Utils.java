package com.aslan.module.utils;

import com.aslan.module.cipher.AlgorithmInfo;
import sts.analyze.Analyze;
import sts.analyze.Item;
import sts.analyze.Result;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static boolean compare(byte[] src, int srcIndex, byte[] dest, int destIndex, int length) {
        int end = srcIndex + length;
        for (int i = srcIndex; i < end; i++) {
            if (src[i] != dest[destIndex + i]) {
                return false;
            }
        }
        return true;
    }

    public static AlgorithmInfo computeAlgorithmInfo(int N, AlgorithmInfo option) {
        if (option == null) option = new AlgorithmInfo();
        int log = (int) log2(N);
        option.N = N;
        option.L = log;
        option.R = log + 1;
        return option;
    }


    public static int roundOf(int value) {

        return 1 << (int) Math.floor(log2(value));
    }

    public static double log2(int value) {

        return (Math.log(value) / Math.log(2));
    }

    public static <T> T find(List<T> list, Filter<T, Boolean> filter) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (filter.accept(list.get(i))) {
                return list.get(i);
            }
        }
        return null;
    }

    public static void reverse(byte[] data) {
        int end = data.length - 1, half = data.length / 2;
        for (int i = 0; i < half; i++) {
            byte temp = data[i];
            data[i] = data[end - i];
            data[end - i] = temp;
        }
    }


    public static void print(byte[] B, String end, int line) {
        for (int i = 0; i < B.length; i++) {
            String hex = Integer.toHexString(B[i] & 0xFF);
            System.out.print((B[i] & 0xFF) < 16 ? "0" + hex : hex);
            System.out.print(' ');
            if (line > 0 && (i + 1) % line == 0) {
                System.out.println();
            }
        }
        System.out.print(end);
    }

    public static void print(byte[] B, String end) {
        print(B, end, -1);
    }

    public static void print(byte[] B) {
        print(B, "\n");
    }

    public static void print(byte[][] B, String end) {
        for (int i = 0; i < B.length; i++) {
            print(B[i], "\n");
        }
        System.out.print(end);
    }

    public static String hex(byte value) {
        String hex = Integer.toHexString(value & 0xFF);
        return (value & 0xFF) < 16 ? "0" + hex : hex;
    }

    public static boolean print(Result[] results) {
        for (Result result : results) {
            if (result != null) {
                Item[] items = result.items;
                System.out.println("\n" + result.name);
                if (items != null) {
                    for (Item item : items) {
                        System.out.println(String.format("%s %6f %s", new Object[]{item.name, Double.valueOf(item.value), item.success ? "SUCCESS" : "FAIL"}));
                    }
                } else {
                    System.out.println(String.format("%6f %s", new Object[]{Double.valueOf(result.value), result.success ? "SUCCESS" : "FAIL"}));
                }
            } else {
                System.out.println("---------------------------------------------------null");
            }
        }
        return true;
    }
}