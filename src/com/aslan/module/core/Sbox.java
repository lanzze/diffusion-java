package com.aslan.module.core;

import com.aslan.module.cipher.keys.Box;
import com.aslan.module.utils.Utils;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class Sbox {
    static int BITS = 4;
    static int SIZE = 256;
    static Random random = new SecureRandom();
    static Random rand2 = new SecureRandom();

    public static void main(String[] args) throws IOException {

        byte[] box = make();
        // byte[] box = very();
        adjust(box);
        validate(box, "box");
        byte[] map = map(box);
        validate(map, "map");
        check(box, map);
        print(box);


//        byte[] S = com.aslan.module.cipher.keys.Box.V2.S;
//        validate(S, "box");
//        byte[] map = map(S);
//        validate(map, "map");
//        check(S, map);
//        print(map);


//        equals(S, Box.V2.ENC_BOX);
//         System.out.println();
        byte[] diff = diff(box);
        print2(diff);
    }

    public static byte[] trys() {
        return adjust(make());
    }


    public static byte[] make() {
        byte[] box = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            box[i] = (byte) i;
        }
        return box;
    }

    public static byte[] map(byte[] sbox) {
        short[][] map = new short[SIZE][2];
        for (short i = 0; i < SIZE; i++) {
            map[i] = new short[]{(i), (short) (sbox[i] & 0xFF)};
        }

        Arrays.sort(map, (a, b) -> {
            return a[1] - b[1];
        });
        byte[] now = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            now[i] = (byte) map[i][0];
        }
        return now;
    }

    public static void print(byte[] codes) {
        for (int i = 1; i <= SIZE; i++) {
            String hex = Integer.toHexString(codes[i - 1] & 0xff);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print("(byte)0x" + hex);
            System.out.print(",");
            if (i % 16 == 0) {
                System.out.println();
            }
        }
    }

    public static void print2(byte[] codes) {
        for (int i = 1; i <= SIZE; i++) {
            String hex = Integer.toHexString(codes[i - 1] & 0xff);
            System.out.print(hex);
            System.out.print(" ");
            if (i % 16 == 0) {
                System.out.println();
            }
        }
    }

    public static void check(byte[] S, byte[] _S) {
        boolean valid = true;
        for (int i = 0; i < SIZE; i++) {
            byte v = S[i];
            int _v = _S[v & 0xff] & 0xFF;
            if (_v != i) {
                System.out.println("Check failed at: " + i);
                valid = false;
                break;
            }
        }
        if (valid)
            System.out.println("Check successful\n");
    }


    public static byte[] adjust(byte[] box) {
        for (int i = 0; i < 81; i++) {
            adjust0(box, new SecureRandom());
        }
        adjust1(box, new SecureRandom());
        return box;
    }

    public static byte[] very() {
        while (true) {
            byte[] box = make();
            if ((box[0] & 0xFF) == 0x69) {
                return box;
            }
        }
    }

    public static void adjust0(byte[] box, Random random) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int oi = i << 4 | j;
                int ni = random.nextInt() & 255;
                byte tmp = box[oi];
                box[oi] = box[ni];
                box[ni] = tmp;
            }
        }
    }

    public static void adjust1(byte[] box, Random random) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int oi = i << 4 | j;
                if (!checkCell(box[oi] & 0xFF, oi)) {
                    swap(box, i, j, random);
                }
            }
        }
    }

    public static void swap(byte[] box, int row, int col, Random random) {
        while (true) {
            int oi = row << 4 | col;
            int ni = random.nextInt() & 255;
            int ov = box[oi] & 0xFF;
            int nv = box[ni] & 0xFF;
            if (checkCell(nv, oi) && checkCell(ov, ni)) {
                byte tmp = box[oi];
                box[oi] = box[ni];
                box[ni] = tmp;
                break;
            }
        }
    }

    public static void validate(byte[] box, String name) {
        boolean valid = true;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int oi = i << 4 | j;
                byte v = box[oi];
                if ((v & 0x0F) == j || (v >> 4 & 0x0F) == i) {
                    valid = false;
                    System.out.println(String.format("Validate %s failed at row: %d, col: %d", name, i, j));
                }
            }
        }
        if (valid) System.out.println(String.format("Validate %s successful", name));
    }

    public static byte[] diff(byte[] box) {
        byte[] buf = new byte[256];
        for (int i = 0; i < 256; i++) {
            buf[i] = (byte) diff(box[i] & 0xFF, i);
        }
        return buf;
    }

    public static int diff(int a, int b) {
        int c = 0;
        if ((a & 128) != (b & 128)) c++;
        if ((a & 64) != (b & 64)) c++;
        if ((a & 32) != (b & 32)) c++;
        if ((a & 16) != (b & 16)) c++;
        if ((a & 8) != (b & 8)) c++;
        if ((a & 4) != (b & 4)) c++;
        if ((a & 2) != (b & 2)) c++;
        if ((a & 1) != (b & 1)) c++;
        return c;
    }

    public static boolean checkCell(int cell, int index) {
        if ((cell & 0xF0) == (index & 0xF0) || (cell & 0x0F) == (index & 0x0F)) {
            return false;
        }
        int diff = diff(cell, index);
        return diff >= BITS && diff != 8;
    }

    public static void equals(byte[] S1, byte[] S2) {
        boolean valid = true;
        for (int i = 0; i < SIZE; i++) {
            if (S1[i] == S2[i]) {
                System.out.println(String.format("Check equals failed at row: %d, col: %d", (i >> 4) & 0xF, i & 0xF));
                valid = false;
            }
        }
        if (valid) System.out.println("Check equals successful");
    }
}
