package cn.ydcat.utils.excel.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.listener.ReadListener;

import java.io.InputStream;

/**
 * Excel工具类
 */
public class ExcelUtils {

    /**
     * 读取文件
     * @param inputStream 文件流
     * @param clz 数据类型
     * @param listener 监听器
     */
    public static void read(InputStream inputStream, Class<?> clz, ReadListener<?> listener) {
        read(inputStream, clz, 0, listener);
    }

    /**
     * 读取文件
     * @param inputStream 文件流
     * @param clz 数据类型
     * @param sheetNo 读取sheet页数
     * @param listener 监听器
     */
    public static void read(InputStream inputStream, Class<?> clz, Integer sheetNo, ReadListener<?> listener) {
        ExcelReaderBuilder readerBuilder = EasyExcel.read();
        readerBuilder
                .file(inputStream)
                .head(clz)
                .registerReadListener(listener)
                .sheet(sheetNo)
                .doRead();
    }
}
