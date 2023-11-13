package com.aslan.test;


import java.util.Random;

public class ATest {

    public static void main(String[] args) {
        System.out.println(r.length);
        int max = (int) Math.pow(2, 32);
        for (int i = max - 10; i < max; i++) {
            String code = Encode(i);
            String decode = Decode(code);
            System.out.println(code);
            System.out.println(decode);
        }


    }

    /**
     * 自定义进制(0、O没有加入,容易混淆；同时排除X,用X补位)
     */
    private static char[] r = new char[]{'Q', 'W', 'E', '8', 'A', 'S', '2', 'D', 'Z', '9', 'C', '7', 'P', '5', 'I', 'K', '3', 'M', 'J', 'U', 'F', 'R', '4', 'V', 'Y', 'L', 'T', 'N', '6', 'B', 'G', 'H'};
    /**
     * (不能与自定义进制有重复)
     */
    private static char b = 'X';
    /**
     * 进制长度
     */
    private static int binLen = r.length;
    /**
     * 生成的邀请码长度
     */
    private static int s = 6;

    /**
     * 根据ID生成六位随机邀请码
     *
     * @param  id
     * @return 随机码
     */
    public static String Encode(long id) {
        char[] buf = new char[32];
        int charPos = 32;

        while ((id / binLen) > 0) {
            int ind = (int) (id % binLen);
            buf[--charPos] = r[ind];
            id /= binLen;
        }
        buf[--charPos] = r[(int) (id % binLen)];
        String str = new String(buf, charPos, (32 - charPos));
        //不够长度的自动随机补全
        if (str.length() < s) {
            StringBuilder sb = new StringBuilder();
            sb.append(b);
            Random rnd = new Random();
            for (int i = 1; i < s - str.length(); i++) {
                sb.append(r[rnd.nextInt(binLen)]);
            }
            str += sb.toString();
        }
        return str;
    }

    /**
     * 根据随机邀请码获得UserId
     *
     * @param  code
     * @return ID
     */
    public static String Decode(String code) {
        char[] chs = code.toCharArray();
        long res = 0L;
        for (int i = 0; i < chs.length; i++) {
            int ind = 0;
            for (int j = 0; j < binLen; j++) {
                if (chs[i] == r[j]) {
                    ind = j;
                    break;
                }
            }
            if (chs[i] == b) {
                break;
            }
            if (i > 0) {
                res = res * binLen + ind;
            } else {
                res = ind;
            }
        }
        return String.valueOf(res);
    }
}
