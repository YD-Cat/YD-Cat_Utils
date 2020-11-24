import cn.ydcat.utils.md5.MD5Utils;
import org.junit.Assert;
import org.junit.Test;

public class TestMain {
    @Test
    public void test(){
        String s = MD5Utils.encodeToMD5("YD-Cat", "123");
        Assert.assertEquals("D8D05DD2616CE4D1791509BE98BAD78B", s);
    }
}
