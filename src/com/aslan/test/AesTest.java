package com.aslan.test;

import com.aslan.module.utils.Utils;
import sts.Sts;
import sts.analyze.Result;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class AesTest {



    public static void main(String[] args) throws Exception{

//        byte[] password = new byte[32];
//
//        KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
//
//        kgen.init(256,new SecureRandom(password));// 利用用户密码作为随机数初始化出
//        // 128位的key生产者
//        //加密没关系，SecureRandom是生成安全随机数    序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
//
//        SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
//
//        byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
//        // null。
//
//        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
//
//        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
//
//        byte[] byteContent = new byte[256];
//        Random random = new SecureRandom();
//        random.nextBytes(byteContent);
//
//        cipher.init(Cipher.ENCRYPT_MODE,key);// 初始化为加密模式的密码器
//
//        byte[] result = cipher.doFinal(byteContent);// 加密
//
//
//        Result[] results = Sts.from(result, result.length * 8);
//
//        Utils.print(results);

        String base64 = "Ans1ygKEBQ/bRlq4bS+9tqYgh5yHe7l90FwnYpAgjLha1HLxUbycXTIE8MfUtuHvJvUlQeovla0DveWUraSZ+7qKKGMLaL0K6jwKGA3cdYucqjMoxbNXS7T+cHQzOHF9D+w6t9WLqMCvoLCaLZ99V0J7B/H1bAltyczUI8mZya8wpplM7LHZ1mgaAur2JlGR7cBp5cCSQpu0sQDToBywQScfRO1Fv7CQLhStaoMhB8kC0Ng7yGPO/5MqpQWO4ZHtgo+NQbobRJ87dgS3k3YefjLgJNmNZwRr9H/eh+YAmES8ADp8x1HAjRyJbXdX7XPFPp/2sPcc93TwybsDV/632zSYVQ8QInRoHjmdSKiwX6GTzoa4bFZPFpByAUajAXjgB7P0YkwodB4bechfAHV/4vI7UtgK0WFefIZX+smoTARdrgMWeD5c08Sm8e5tD0q+";
        byte[] decode = Base64.getDecoder().decode(base64);

        Result[] results = Sts.from(decode, decode.length * 8);
//
        Utils.print(results);

    }
}
