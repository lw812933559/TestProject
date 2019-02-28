package com.xinghai.springboot;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class HelloController {

    /**
     * 登录
     * @param response 返回给浏览器的响应报文
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String login(HttpServletResponse response){
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        return "欢迎登陆Spring boot服务器";
    }

    /**
     * 检查Cookie
     * @param request 浏览器传过来的请求报文
     * @return
     */
    @RequestMapping(value = "/checkCookie",method = RequestMethod.GET)
    public String checkCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)){
            return "请传人正确的cookie";
        }

        for (Cookie c : cookies){
            if (c.getName().equals("login") && c.getValue().equals("true")){
                return "查询成功，传入的cookie正确";
            }
        }

        return "请传人正确的cookie";
    }

    /**
     * 带参数访问的get方法
     * url格式：localhost:port/questSubInfo?name=zhangsan&sex=man
     * @param name
     * @param sex
     * @return
     */
    @RequestMapping(value = "/questSubInfo",method = RequestMethod.GET)
    public Map questSubInfo1(@RequestParam String name,@RequestParam String sex){
        //入参判断
        if (!(name.equals("zhangsan") && sex.equals("man"))){
            return null;
        }
        //业务处理
        Map map = new HashMap();
        map.put("name","zhangsan");
        map.put("age","18");
        map.put("sex","man");

        System.out.println("有一个用户发起查询");
        return map;
    }

    /**
     * 带参数访问的get方法
     * url格式：localhost:port/questSubInfo/zhangsan/man
     * @param name
     * @param sex
     * @return
     */
    @RequestMapping(value = "/questSubInfo/{name}/{sex}",method = RequestMethod.GET)
    public Map questSubInfo2(@PathVariable String name,@PathVariable String sex){
        //入参判断
        if (!(name.equals("zhangsan") && sex.equals("man"))){
            return null;
        }
        //业务处理
        Map map = new HashMap();
        map.put("name","zhangsan");
        map.put("age","20");
        map.put("sex","man");
        return map;
    }
}
