public class Main {

    public static void main(String[] args) throws Exception {
        int a = 0, b = 0, c = 0, d = 0;
        int m1 = 0, m2 = 0, m3 = 0, m4 = 0;

        for (int i = 0; i < 256; i++) {
            int A = (b ^ d ^ a) ^ (((~a & 0xFF) >> 4) | m1);
            int C = (d ^ A ^ c) ^ (((~c & 0xFF) << 3) | m2);
            int B = (A ^ C ^ b) ^ (((~b & 0xFF) << 2) | m3);
            int D = (C ^ B ^ d) ^ (((~d & 0xFF) >> 1) | m4);
            System.out.println(String.format("a=%2x,b=%2x,c=%2x,d=%2x", A, B, C, D));
            a++;
        }
    }
}
