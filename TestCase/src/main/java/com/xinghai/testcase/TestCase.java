package com.xinghai.testcase;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ResourceBundle;

public class TestCase {
    private String baseUrl,checkCookieUri,querySub1Uri,querySub2Uri;
    private CookieStore cookieStore;

    @BeforeMethod
    public void init(){
        ResourceBundle bundle = ResourceBundle.getBundle("testProperty");
        baseUrl = bundle.getString("base.url");
        checkCookieUri = bundle.getString("checkCookie.uri");
        querySub1Uri = bundle.getString("querysub1.uri");
        querySub2Uri = bundle.getString("querysub2.uri");
    }


    @Test
    public void loginTest() throws IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(baseUrl);
        HttpResponse response = client.execute(get);
        String responseStr = EntityUtils.toString(response.getEntity());

        cookieStore = client.getCookieStore();

        Assert.assertEquals(responseStr,"欢迎登陆Spring boot服务器");
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void checkCookieTest() throws IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        client.setCookieStore(cookieStore);
        HttpGet get = new HttpGet(baseUrl+checkCookieUri);
        HttpResponse response = client.execute(get);
        String s = EntityUtils.toString(response.getEntity());

        Assert.assertEquals(s,"查询成功，传入的cookie正确");
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void querySub1Test() throws IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(baseUrl+querySub1Uri);
        HttpResponse response = client.execute(get);
        String s = EntityUtils.toString(response.getEntity());

        Assert.assertEquals(s,"{\"sex\":\"man\",\"name\":\"zhangsan\",\"age\":\"18\"}");
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void querySub2Test() throws IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(baseUrl+querySub2Uri);
        HttpResponse response = client.execute(get);
        String s = EntityUtils.toString(response.getEntity());

        Assert.assertEquals(s,"{\"sex\":\"man\",\"name\":\"zhangsan\",\"age\":\"20\"}");
    }
}
