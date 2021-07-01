package com.baizhi.mysqlredis.collctor;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Controller
@RequestMapping("user")
public class AdminAction  {

    HashMap<String,Object> map=new HashMap<>();
//    @EnableTransactionManagement(proxyTargetClass = false,
//    @Import(AutoProxyRegistrar.class)

    @RequestMapping("login")
    public String queryByNamePas(HttpSession session, HttpServletResponse response, String name, String password) throws IOException {
        //AbstractAutowireCapableBeanFactory
        //首先获取对象
        if (name.equals("wjb")){
            //用户名正确
            if (password.equals("55")){
                //密码正确  跳转
                map.put("status",100);
                map.put("message","密码正确");
            }else{
                //密码错误
                map.put("status",104);
                map.put("message","密码错误");
            }
        }else {
            //用户名错误
            map.put("status",105);
            map.put("message","用户不存在");
        }
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String maps = JSON.toJSONString(map);
        session.setAttribute("user","wjb");
        writer.println(maps);
        return  null;
    }

}
