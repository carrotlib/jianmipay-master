package com.carrotlib.jianmipay.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RPCSignUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RPCSignUtil.class);

    public static String sha1(String desript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(desript.getBytes());
            byte messageDigest[] = digest.digest();
            //Create Hex String
            StringBuffer hexString = new StringBuffer();
            //字节数组转换为十六进制数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
             LOGGER.debug("MessageDigest.getInstance error!", e);
        }

        return "";
    }
}
