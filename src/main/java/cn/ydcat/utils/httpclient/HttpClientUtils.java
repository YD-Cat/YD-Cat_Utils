package cn.ydcat.utils.httpclient;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClient工具
 * @author YD-Cat
 * @date 2020年08月13日
 */
public class HttpClientUtils {

    /**
     * 构建get请求参数
     * @param params 请求参数
     * @return 请求参数列表
     */
    public static List<NameValuePair> buildGetParam(Map<String, String> params){
        if(params == null) {
            throw new NullPointerException();
        }
        List<NameValuePair> param= new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            param.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return param;
    }

    /**
     * 构建post请求参数
     * @param params 请求参数
     * @return 请求参数列表
     * @throws UnsupportedEncodingException 构建失败
     */
    public static UrlEncodedFormEntity buildPostEntity(Map<String, String> params) throws UnsupportedEncodingException {
        if(params == null) {
            throw new NullPointerException();
        }
        List<NameValuePair> param= new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            param.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(param,"UTF8");
        return formEntity;
    }

    /**
     * 构建URI
     * @param url 请求地址
     * @return 请求链接
     * @throws URISyntaxException 请求链接构建异常
     */
    public static URI buildURI(String url) throws URISyntaxException {
        return HttpClientUtils.buildURI(url, null);
    }

    /**
     * 构建URI
     * @param url 请求地址
     * @param params 请求参数
     * @return 请求链接
     * @throws URISyntaxException 请求链接构建异常
     */
    public static URI buildURI(String url, Map<String, String> params) throws URISyntaxException {
        if(url == null) {
            throw new NullPointerException();
        }
        URIBuilder uriBuilder = new URIBuilder(url);
        if(params != null) {
            uriBuilder.setParameters(HttpClientUtils.buildGetParam(params));
        }
        return uriBuilder.build();
    }

    /**
     * 发送不带参数的get请求，默认请求头
     * @param url 请求链接
     * @return 服务器返回结果
     * @throws IOException 请求失败
     * @throws URISyntaxException 请求链接构建失败
     */
    public static HttpResult sendGet(String url) throws IOException, URISyntaxException {
        if(url == null) {
            throw new NullPointerException();
        }
        return HttpClientUtils.sendGet(url, null, null);
    }

    /**
     * 发送带参数的get请求，默认请求头
     * @param url 请求地址
     * @param params 请求参数
     * @return 服务器返回结果
     * @throws URISyntaxException 请求链接构建失败
     * @throws IOException 请求失败
     */
    public static HttpResult sendGet(String url, Map<String, String> params) throws URISyntaxException, IOException {
        if(url == null) {
            throw new NullPointerException();
        }
        return HttpClientUtils.sendGet(url, params, null);
    }

    /**
     * 发送get请求，可自定义参数和请求头
     * @param url 请求链接
     * @param params 请求参数
     * @param headers 请求头
     * @return 服务器返回结果
     * @throws URISyntaxException 请求链接构建失败
     * @throws IOException 请求失败
     */
    public static HttpResult sendGet(String url, Map<String, String> params, Map<String, String> headers) throws URISyntaxException, IOException {
        if(url == null) {
            throw new NullPointerException();
        }
        // 构建请求链接
        URI uri= HttpClientUtils.buildURI(url,params);
        // 构建get请求
        HttpGet httpGet = new HttpGet(uri);
        // 构建请求头
        if(headers != null) {
            Header[] headersArray = HttpClientUtils.buildHeader(headers);
            httpGet.setHeaders(headersArray);
        }
        // 发送请求，接收结果
        HttpResult result= HttpClientUtils.execute(httpGet);
        return result;
    }

    /**
     * 发送不带参数的post请求，默认请求头
     * @param url 请求链接
     * @return 服务器返回结果
     * @throws URISyntaxException 请求链接构建失败
     * @throws IOException 请求失败
     */
    public static HttpResult sendPost(String url) throws URISyntaxException, IOException {
        if(url == null) {
            throw new NullPointerException();
        }
        return HttpClientUtils.sendGet(url, null, null);
    }

    /**
     * 发送带参数的post请求，默认请求头
     * @param url 请求链接
     * @param params 请求参数
     * @return 服务器返回结果
     * @throws URISyntaxException 请求链接构建失败
     * @throws IOException 请求失败
     */
    public static HttpResult sendPost(String url, Map<String, String> params) throws URISyntaxException, IOException {
        if(url == null) {
            throw new NullPointerException();
        }
        return HttpClientUtils.sendPost(url, params, null);
    }

    /**
     * 发送post请求，可自定义参数和请求头
     * @param url 请求链接
     * @param params 请求参数
     * @param headers 请求头
     * @return 服务器返回结果
     * @throws URISyntaxException 请求链接构建失败
     * @throws IOException 请求失败
     */
    public static HttpResult sendPost(String url, Map<String, String> params, Map<String, String> headers) throws URISyntaxException, IOException {
        if(url == null) {
            throw new NullPointerException();
        }
        // 构建请求链接
        URI uri = HttpClientUtils.buildURI(url, null);
        // 构建post请求
        HttpPost httpPost = new HttpPost(uri);
        // 构建请求参数
        if(params != null) {
            UrlEncodedFormEntity entity = HttpClientUtils.buildPostEntity(params);
            httpPost.setEntity(entity);
        }
        // 构建请求头
        if(headers != null) {
            Header[] headersArray = HttpClientUtils.buildHeader(headers);
            httpPost.setHeaders(headersArray);
        }
        // 发送请求，接收结果
        HttpResult result= HttpClientUtils.execute(httpPost);
        return result;
    }

    /**
     * 发送post请求，json参数
     *
     * @param url 请求路径
     * @param json json参数
     * @return 请求结果
     * @throws URISyntaxException 请求链接构建失败
     * @throws IOException 请求失败
     */
    public static HttpResult sendPost(String url, String json) throws URISyntaxException, IOException {
        return HttpClientUtils.sendPostJson(url, json, null);
    }

    /**
     * 发送post请求，json参数，可自定义请求头
     * @param url 请求路径
     * @param json json参数
     * @param headers 请求头
     * @return 请求结果
     * @throws URISyntaxException 请求链接构建失败
     * @throws IOException 请求失败
     */
    public static HttpResult sendPostJson(String url, String json, Map<String, String> headers) throws URISyntaxException, IOException {
        if(url == null) {
            throw new NullPointerException();
        }
        // 构建请求链接
        URI uri = HttpClientUtils.buildURI(url);
        // 构建post请求
        HttpPost httpPost = new HttpPost(uri);
        // 构建请求参数
        StringEntity entity = new StringEntity(json,"utf-8"); //解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-Type", "application/json");
        // 构建请求头
        if(headers != null) {
            Header[] headersArray = HttpClientUtils.buildHeader(headers);
            httpPost.setHeaders(headersArray);
        }
        // 发送请求，接收结果
        HttpResult result= HttpClientUtils.execute(httpPost);
        return result;
    }

    /**
     * 发送post请求，xml参数，可自定义请求头
     * @param url 请求路径
     * @param xml 待发送的xml
     * @param headers 请求头
     * @param sslsf 证书
     * @return 请求结果
     * @throws IOException 请求失败
     */
    public static HttpResult sendPostXml(String url, String xml, Map<String, String> headers, SSLConnectionSocketFactory sslsf) throws IOException{
        HttpPost httpPost = new HttpPost(url);
        StringEntity se = new StringEntity(xml,"utf-8");
        httpPost.setEntity(se);
        httpPost.addHeader("Content-Type", "text/xml");
        // 构建请求头
        if(headers != null) {
            Header[] headersArray = HttpClientUtils.buildHeader(headers);
            httpPost.setHeaders(headersArray);
        }
        HttpResult result = HttpClientUtils.execute(httpPost, sslsf);
        return result;
    }

    //------------------私有方法------------------//

    /**
     * 将请求头部信息从Map转换为Header
     * @param headerMap 请求头部Map类型
     * @return Header数组
     */
    private static Header[] buildHeader(Map<String, String> headerMap){
        if(headerMap == null){
            throw new NullPointerException();
        }
        Header[] headers = new Header[headerMap.size()];
        int index = 0;
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            BasicHeader basicHeader = new BasicHeader(entry.getKey(), entry.getValue());
            headers[index]=basicHeader;
            index++;
        }
        return headers;
    }

    /**
     * 发送请求
     * @param request 请求
     * @return 请求结果
     * @throws IOException 请求失败
     */
    private static HttpResult execute(HttpUriRequest request) throws IOException {
        if(request == null) {
            throw new NullPointerException();
        }
        // 打开了浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 发送请求
        CloseableHttpResponse response = httpClient.execute(request);
        return HttpClientUtils.getResult(response);
    }

    /**
     * 发送请求，带证书
     * @param request 请求
     * @param sslsf ssl证书
     * @return 请求结果
     * @throws IOException 请求失败
     */
    private static HttpResult execute(HttpUriRequest request,SSLConnectionSocketFactory sslsf) throws IOException {
        if(request == null) {
            throw new NullPointerException();
        }
        // 打开了浏览器
        CloseableHttpClient httpClient=null;
        if(sslsf==null) {
            httpClient = HttpClients.createDefault();
        }else {
            httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        }
        // 发送请求
        CloseableHttpResponse response = httpClient.execute(request);
        return HttpClientUtils.getResult(response);
    }

    /**
     * 公用方法，获取返回结果
     * @param response 返回响应
     * @return 响应结果
     * @throws IOException 响应结果转换成字符串失败，或者响应关闭失败
     */
    private static HttpResult getResult(CloseableHttpResponse response) throws IOException {
        if(response == null) {
            throw new NullPointerException();
        }
        HttpResult result = new HttpResult();
        // 公用
        String content = EntityUtils.toString(response.getEntity());
        int code = response.getStatusLine().getStatusCode();
        result.setCode(code);
        result.setResult(content);
        response.close();
        return result;
    }
}
