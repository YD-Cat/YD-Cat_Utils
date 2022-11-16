package cn.ydcat.utils.zip;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

/**
 * 压缩包工具类
 */
public class ZipUtils {

    /**
     * 压缩文件，不允许压缩文件夹
     * @param zos 压缩包输出流
     * @param file 待压缩文件
     * @param basePath 压缩路径
     * @throws IOException
     */
    public static void compressFile(ZipOutputStream zos, File file, String basePath) throws IOException {
        if(file.isDirectory()){
            throw new ZipException("compressFile不允许压缩文件夹");
        }
        if (!basePath.endsWith("/")) {
            basePath += "/";
        }
        String path = basePath + file.getName();
        zos.putNextEntry(new ZipEntry(path));

        FileInputStream fis = new FileInputStream(file);
        int len = 0;
        byte[] bytes = new byte[2048];
        while ((len = fis.read(bytes)) > 0) {
            zos.write(bytes, 0, len);
        }
        zos.closeEntry();
        fis.close();
    }

    /**
     * 压缩文件，支持压缩文件夹
     * @param zos 压缩包输出流
     * @param file 压缩文件
     * @param basePath 压缩路径
     * @throws IOException
     */
    public static void compress(ZipOutputStream zos, File file, String basePath) throws IOException {
        if (!basePath.endsWith("/")) {
            basePath += "/";
        }
        if (file.isFile()) {
            compressFile(zos, file, basePath);
        }else if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File subFile : files) {
                if(subFile.isDirectory()){
                    String path = basePath + subFile.getName()+"/";
                    if (subFile.listFiles().length > 0) {
                        compress(zos, subFile, path);
                    }else {
                        zos.putNextEntry(new ZipEntry(path+"/"));
                        zos.closeEntry();
                    }
                }else {
                    compressFile(zos, subFile, basePath);
                }
            }
        }
    }


}
