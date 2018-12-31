package com.aslan.test;

public class ATest {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i++) {
            System.out.print(String.format("%2d >> 1 = %2d\n", i, i >> 1));
        }
    }
}
