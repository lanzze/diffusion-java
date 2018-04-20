package com.aslan.module.utils;

import com.aslan.module.cipher.AlgorithmInfo;

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

    public static AlgorithmInfo computeAlgorithmInfo(int N, int e, AlgorithmInfo option) {
        int lg = (int) log2(N);
        if (option == null) option = new AlgorithmInfo();
        option.N = N;
        option.L = lg;
        option.R = lg + e;
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

}