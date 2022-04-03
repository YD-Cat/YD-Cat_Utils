package cn.ydcat.utils.gzip;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtil {
    /**
     * 压缩对象
     * @param obj 待压缩对象
     * @return 二进制数组
     * @throws Exception
     */
    public static byte[] writeCompressObject(Object obj) throws Exception {
        if (obj == null) {
            throw new NullPointerException("压缩对象 obj 为空");
        }
        byte[] byteData = null;
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        GZIPOutputStream gzout = null;
        ObjectOutputStream out = null;
        try {
            gzout = new GZIPOutputStream(baout);
            out = new ObjectOutputStream(gzout);
        } finally {
            out.writeObject(obj);
            out.flush();
            out.close();
            gzout.close();
            baout.close();
        }
        byteData = baout.toByteArray();
        return byteData;
    }

    /**
     * 解压二进制数组
     * @param byteData 带解压二进制数组
     * @param t 原对象
     * @param <T> 原对象类型
     * @return
     * @throws Exception
     */
    public static <T>T  readCompressObject(byte[] byteData, Class<T> t) throws Exception {
        if (byteData == null) {
            throw new NullPointerException("二进制 byteData 为空");
        }
        Object obj = null;
        ByteArrayInputStream bain = new ByteArrayInputStream(byteData);
        GZIPInputStream gzin = null;
        ObjectInputStream in = null;
        try {
            gzin = new GZIPInputStream(bain);
            in = new ObjectInputStream(gzin);
        } finally {
            if (in != null) {
                obj = in.readObject();
                in.close();
            }
            if (bain != null) {
                bain.close();
            }
            if (gzin != null) {
                gzin.close();
            }
        }
        return t.cast(obj);
    }
}
