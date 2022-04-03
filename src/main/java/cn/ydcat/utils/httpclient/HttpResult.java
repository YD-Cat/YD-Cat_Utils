package cn.ydcat.utils.httpclient;

/**
 * http请求结果
 * @author LiuYingKe
 * @date 2020年08月14日
 */
public class HttpResult{
    private int code;
    private String message;
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HttpResult [code=" + code + ", message=" + message + ", result=" + result + "]";
    }
}