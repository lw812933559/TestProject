package com.xinghai.testngDemo;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class TestCase01 {
    private String url,loginUri,querySubUri;
    private ResourceBundle bundle;
    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
        loginUri = bundle.getString("login.uri");
        querySubUri = bundle.getString("querySub.uri");
    }
    @Test
    public void test() throws IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url+loginUri);
        HttpResponse response = client.execute(httpGet);
        //获取服务器返回报文
        String responseStr = EntityUtils.toString(response.getEntity());
        //获取Cookie信息
        CookieStore cookieStore = client.getCookieStore();
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies){
            String name = cookie.getName();
            String value = cookie.getValue();
        }
    }
}
