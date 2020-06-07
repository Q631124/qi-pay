package com.pay.util.identity;

import java.security.MessageDigest;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;




public class EncryptionUtil
{
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[((b[i] & 0xF0) >>> 4)]);
            sb.append(HEX_DIGITS[(b[i] & 0xF)]);
        }
        return sb.toString();
    }




    public static String md5Bit32(String sourceString)
            throws Exception
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(sourceString.getBytes());
            byte[] messageDigest = digest.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }





    public static String md5Bit16(String sourceString)
            throws Exception
    {
        return md5Bit32(sourceString).substring(8, 24);
    }

    public static String base64Encode(String sourceString)
            throws Exception
    {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(sourceString.getBytes());
    }



    public static String base64Decode(String sourceString)
            throws Exception
    {
        try
        {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(sourceString);
            return new String(bytes);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}

