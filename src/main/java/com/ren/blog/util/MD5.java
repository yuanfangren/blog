package com.ren.blog.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * md5加密 org.apache.commons
 * @author RYF
 *
 */
public class MD5 {
	
	/**
     * MD5方法
     * 
     * @param text 明文
     * @param key 密钥
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text, String key) throws Exception {
        return DigestUtils.md5Hex(text + key);
    }

    /**
     * MD5验证方法
     * 
     * @param text 明文
     * @param key 密钥
     * @param md5 密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String key, String md5) throws Exception {
        //根据传入的密钥进行验证
        String md5Text = md5(text, key);
        if(md5Text.equalsIgnoreCase(md5)){
            return true;
        }
        return false;
    }
}
