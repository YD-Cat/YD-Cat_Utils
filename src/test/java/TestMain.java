import cn.ydcat.utils.md5.MD5Utils;
import cn.ydcat.utils.zip.ZipUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

public class TestMain {
    @Test
    public void test() throws IOException {
        FileOutputStream fos = new FileOutputStream("D:\\ttt.zip");
        File file1 = new File("D:\\1.xls");
        File file2 = new File("D:\\2.xls");
        File file3 = new File("D:\\3.xls");

        ZipOutputStream zos = new ZipOutputStream(fos);
        ZipUtils.compressFile(zos, file1, "1");
        ZipUtils.compressFile(zos, file2, "2");
        ZipUtils.compressFile(zos, file3, "3");

        fos.flush();
        fos.close();
    }
}
