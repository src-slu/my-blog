package com.ls.springcloud.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName Md5Utils
 * @Description
 * @Author lushuai
 * @Date 2019/11/18 16:44
 */
public class Md5Utils {

    public static String encryptMd5(String key, String security){
        MessageDigest messageDigest = null;
        StringBuilder sbuilder = null;
        try {
            sbuilder = new StringBuilder(key);
            String sign = sbuilder.append(security).toString();

            messageDigest = MessageDigest.getInstance("md5");
            messageDigest.update(sign.getBytes("utf-8"));
            /**
             * 将密文转化为十六进制字符串
             */
            byte[] digest = messageDigest.digest();
            sbuilder = new StringBuilder();
            for(int i=0; i<digest.length; i++){
                int v = digest[i] & 0xFF;
                String hexString = Integer.toHexString(v);

                if(hexString.length() > 2){
                    sbuilder.append(0);
                }
                sbuilder.append(hexString);
            }

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return sbuilder.toString().toUpperCase();
    }
}
