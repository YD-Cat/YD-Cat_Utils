package cn.ydcat.utils.file;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * 文件工具类
 * @author YD-Cat
 * @date 2020/9/27 21:21
 */
public class FileUtils {
    /**
     * 从网络获取文件
     * @param urlPath 网络路径
     * @param saveFile 保存的文件
     * @return
     * @throws IOException
     */
    public static boolean getFile4Url(String urlPath, File saveFile) throws IOException {
        URL url=new URL(urlPath);
        URLConnection conn = url.openConnection();
        InputStream is=conn.getInputStream();
        byte[] data=new byte[1024];
        int length;
        OutputStream os=new FileOutputStream(saveFile);
        while ((length=is.read(data))>0){
            os.write(data,0,length);
        }
        os.flush();
        os.close();
        is.close();
        return true;
    }

    /**
     * 从网络获取文件
     * @param urlPath 网络路径
     * @param savePath 保存的文件路径
     * @return
     * @throws IOException
     */
    public static boolean getFile4Url(String urlPath, String savePath) throws IOException {
        File saveFile=new File(savePath);
        return getFile4Url(urlPath, saveFile);
    }
}
