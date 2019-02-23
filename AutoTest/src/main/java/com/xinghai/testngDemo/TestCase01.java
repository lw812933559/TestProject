package com.xinghai.testngDemo;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class TestCase01 {
    private String url,loginUri,querySubUri,commitUri;
    private ResourceBundle bundle;
    private CookieStore cookieStore;
    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
        loginUri = bundle.getString("login.uri");
        querySubUri = bundle.getString("querySub.uri");
        commitUri = bundle.getString("commit.uri");
    }
    @Test
    public void testLogin() throws IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url+loginUri);
        HttpResponse response = client.execute(httpGet);
        //获取服务器返回报文
        String responseStr = EntityUtils.toString(response.getEntity());

        System.out.println("登录结果："+responseStr);
        //获取Cookie信息
        cookieStore = client.getCookieStore();
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("Cookies : "+name+" , "+value);
        }
    }
    @Test(dependsOnMethods = {"testLogin"})
    public void testQuerySub() throws IOException {
        HttpGet get = new HttpGet(url+querySubUri);
        DefaultHttpClient client = new DefaultHttpClient();
        client.setCookieStore(cookieStore);
        HttpResponse httpResponse = client.execute(get);
        String response = EntityUtils.toString(httpResponse.getEntity());
        int resultcode = httpResponse.getStatusLine().getStatusCode();
        if (resultcode == 200){
            System.out.println("查询结果："+response);
        }
    }
    @Test(dependsOnMethods = {"testLogin"})
    public void testCommitData() throws IOException {
        HttpPost post = new HttpPost(url+commitUri);
        JSONObject jobj = new JSONObject();
        jobj.put("msg","test123");
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(jobj.toString(),"utf-8");
        post.setEntity(entity);

        DefaultHttpClient client = new DefaultHttpClient();
        client.setCookieStore(cookieStore);
        HttpResponse httpResponse = client.execute(post);
        String response = EntityUtils.toString(httpResponse.getEntity());
        int resultCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("数据提交结果码："+resultCode);
        if(resultCode == 200){
            System.out.println("数据提交相应："+response);
        }
    }
}
