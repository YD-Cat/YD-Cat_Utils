package cn.ydcat.utils.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * @author YD-Cat
 * @date 2020/9/27 21:15
 */
public class MD5Utils {
    /**
     * MD5加密
     * @param str 待加密字符串
     * @return 32位大写MD5
     */
    public static String encodeToMD5(String str){
        StringBuffer MD5=new StringBuffer();
        try{
            MessageDigest messageDigest= MessageDigest.getInstance("md5");
            byte[] diges=messageDigest.digest(str.getBytes());
            for(byte b:diges){
                //保留后八位，计算补码
                //保留后八位，即16进制中的后两位，在第9位改为1，即16进制中第3位改为1
                MD5.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1,3));
            }
            return MD5.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断某字符串是否和某MD5相等
     * @param str 未加密字符串
     * @param MD5 MD5
     * @return
     */
    public static boolean isEquals(String str,String MD5){
        if(str==null || "".equals(str) || MD5==null || "".equals(MD5))
            return false;
        return MD5.toUpperCase().equals(encodeToMD5(str));
    }

}
