package com.aslan.module.encoding;

public class Hex {
    static byte[] table = new byte[]{
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            -1, -1, -1, -1, -1, -1, -1,
            'a', 'b', 'c', 'd', 'e', 'f',
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            'A', 'B', 'C', 'D', 'E', 'F'};

    public static byte[] h2b(String hex) {
        hex = hex.replaceAll("\\s+", "");
        int length = hex.length();
        if ((length & 1) == 1) throw new Error("长度不正确，字符必须成对！");
        byte[] buf = new byte[length >>> 1];
        for (int i = 0, j = 0; i < length; i += 2, j++) {
            int h = table[hex.charAt(i)];
            int l = table[hex.charAt(i + 1)];
            if (h < 0 || h > table.length) {
                throw new RuntimeException("无效字符：" + hex.charAt(i));
            }
            if (l < 0 || l > table.length) {
                throw new RuntimeException("无效字符：" + hex.charAt(i + 1));
            }
            buf[j] = (byte) (h << 4 | l);
        }
        return buf;
    }

    public static String b2h(byte[] hex) {
        StringBuilder buf = new StringBuilder();
        int length = hex.length;
        for (int i = 0; i < length; i++) {
            int v = hex[i] & 0xFF;
            String s = Integer.toHexString(v);
            buf.append(v <= 16 ? '0' + s : s);
            if (i != length - 1) {
                buf.append(' ');
            }
        }
        return buf.toString();
    }

}