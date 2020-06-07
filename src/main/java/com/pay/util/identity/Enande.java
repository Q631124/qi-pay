package com.pay.util.identity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by tolink on 2019-03-27.
 */
public class Enande {
    /**
     * 加密
     * @param sourceString
     * @return 密文
     */
    public static String encrypt(String sourceString) {
        String encryptStr = "";
        try {
            String password = "12345";
            char[] p = password.toCharArray(); // 字符串转字符数组
            int n = p.length; // 密码长度
            char[] c = sourceString.toCharArray();
            int m = c.length; // 字符串长度
            for (int k = 0; k < m; k++) {
                int mima = c[k] + p[k % n] - 49; // 加密
                c[k] = (char) mima;
            }
            encryptStr = new String(c);
            encryptStr = URLEncoder.encode(encryptStr, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encryptStr;
    }

    /**
     * 解密
     * @param sourceString
     * @return 明文
     */
    public static String decrypt(String sourceString) {
        String decrypt = "";
        try {
            sourceString = URLDecoder.decode(sourceString, "utf-8");
            String password = "12345";
            char[] p = password.toCharArray(); // 字符串转字符数组
            int n = p.length; // 密码长度
            char[] c = sourceString.toCharArray();
            int m = c.length; // 字符串长度
            for (int k = 0; k < m; k++) {
                int mima = c[k] - p[k % n] + 49; // 解密
                c[k] = (char) mima;
            }
            decrypt = new String(c);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decrypt;
    }

}
