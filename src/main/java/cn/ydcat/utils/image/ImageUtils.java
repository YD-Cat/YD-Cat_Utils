package cn.ydcat.utils.image;

import cn.ydcat.utils.file.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

/**
 * 图片工具类
 * @author YD-Cat
 * @date 2020/9/27 21:21
 */
public class ImageUtils {
    /**
     * <p>合并两张图片</p>
     * <p>X轴和Y轴以背景图左上角为原点，向右向下为正</p>
     *
     * @param baseImage  背景图
     * @param coverImage 覆盖图
     * @param x          X轴覆盖起始点
     * @param y          Y轴覆盖起始点
     * @return 背景图的BufferedImage，合并失败为null
     * @throws IOException
     */
    public static BufferedImage coverImage(File baseImage, File coverImage, int x, int y) throws IOException {
        BufferedImage baseImageBI = ImageIO.read(baseImage);
        BufferedImage coverImageBI = ImageIO.read(coverImage);
        // 覆盖图的宽高
        int width = coverImageBI.getWidth();
        int height = coverImageBI.getHeight();
        // 合并图片
        Graphics2D graphics = baseImageBI.createGraphics();
        boolean b = graphics.drawImage(coverImageBI, x, y, width, height, null);
        if (b) {
            return baseImageBI;
        } else {
            return null;
        }
    }

    /**
     * <p>合并两张图片</p>
     * <p>X轴和Y轴以背景图左上角为原点，向右向下为正</p>
     *
     * @param baseImage  背景图
     * @param coverImage 覆盖图
     * @param x          X轴覆盖起始点
     * @param y          Y轴覆盖起始点
     * @param width      覆盖图输出宽度
     * @param height     覆盖图输出高度
     * @return 背景图的BufferedImage，合并失败为null
     * @throws IOException
     */
    public static BufferedImage coverImage(File baseImage, File coverImage, int x, int y, int width, int height) throws IOException {
        BufferedImage baseImageBI = ImageIO.read(baseImage);
        BufferedImage coverImageBI = ImageIO.read(coverImage);
        // 合并图片
        Graphics2D graphics = baseImageBI.createGraphics();
        boolean b = graphics.drawImage(coverImageBI, x, y, width, height, null);
        if (b) {
            return baseImageBI;
        } else {
            return null;
        }
    }

    /**
     * 输出图片到指定路径和指定名称
     *
     * @param image 图片BufferedImage对象
     * @param file  保存的图片文件
     * @return boolean 输出结果
     * @throws IOException
     */
    public static boolean writeImage(BufferedImage image, File file) throws IOException {
        String path=file.getAbsolutePath();
        // 获取输出格式
        int index = path.lastIndexOf(".") + 1;
        String formatName = path.substring(index);

        return ImageIO.write(image, formatName, file);
    }

    /**
     * 输出图片到指定路径和指定名称
     *
     * @param image 图片BufferedImage对象
     * @param path  保存的图片路径
     * @return boolean 输出结果
     * @throws IOException
     */
    public static boolean writeImage(BufferedImage image, String path) throws IOException {
        File file = new File(path);
        return writeImage(image, file);
    }

    /**
     * 图片转为base64
     *
     * @param imgPath 图片路径
     * @return
     */
    public static String imageToBase64(String imgPath) throws IOException {
        String encode = null;
        InputStream in = null;
        byte[] data;
        Base64.Encoder encoder = Base64.getEncoder();
        in = new FileInputStream(imgPath);
        data = new byte[in.available()];
        in.read(data);
        encode = encoder.encodeToString(data); // 编码
        in.close();
        return encode;
    }

    /**
     * 图片转为base64
     *
     * @param image 图片文件
     * @return
     */
    public static String imageToBase64(File image) throws IOException {
        String imgPath = image.getAbsolutePath();
        return imageToBase64(imgPath);
    }

    /**
     * base64转为图片并保存
     *
     * @param imgdata 图片base64
     * @param imgPath 图片保存路径
     * @return
     */
    public static boolean base64ToImage(String imgdata, String imgPath) throws IOException{
        if (imgdata == null || "".equals(imgdata)) { // 无效数据
            throw new IOException("imgdata is null");
        } else {
            Base64.Decoder decoder = Base64.getDecoder();
            OutputStream out = null;
            out = new FileOutputStream(imgPath);
            byte[] data = decoder.decode(imgdata); // 解码
            out.write(data);
            out.flush();
            out.close();
            return true;
        }
    }

    /**
     * base64转为图片并保存
     *
     * @param imgdata 图片base64
     * @param file    保存的图片文件
     * @return
     */
    public static boolean base64ToImage(String imgdata, File file) throws IOException {
        String outPath = file.getAbsolutePath();
        return base64ToImage(imgdata, outPath);
    }

    /**
     * 从网络获取图片并保存
     * @param urlPath 网络路径
     * @param saveFile 保存的图片文件
     * @return
     * @throws IOException
     */
    public static boolean getImage4Url(String urlPath, File saveFile) throws IOException {
        return FileUtils.getFile4Url(urlPath,saveFile);
    }

    /**
     * 从网络获取图片并保存
     * @param urlPath 网络路径
     * @param savePath 保存的图片本地路径
     * @return
     * @throws IOException
     */
    public static boolean getImage4Url(String urlPath, String savePath) throws IOException {
        File saveFile=new File(savePath);
        return getImage4Url(urlPath,saveFile);
    }

    /**
     * 高斯模糊
     * @param img 原图片
     * @param radius 模糊权重
     * @return 模糊后图片
     * @throws IOException
     */
    public static BufferedImage blur(BufferedImage img, int radius) throws IOException {
        return GaussianBlurUtil.blur(img, radius);
    }

    /**
     * 高斯模糊
     * @param img 原图片
     * @param radius 模糊权重
     * @return 模糊后图片
     * @throws IOException
     */
    public static BufferedImage blur(File img, int radius) throws IOException {
        BufferedImage file = ImageIO.read(img);
        return blur(file, radius);
    }

    /**
     * 高斯模糊
     * @param path 原图片路径
     * @param radius 模糊权重
     * @return 模糊后图片
     * @throws IOException
     */
    public static BufferedImage blur(String path, int radius) throws IOException {
        BufferedImage img = ImageIO.read(new File(path));
        return blur(img, radius);
    }
}
