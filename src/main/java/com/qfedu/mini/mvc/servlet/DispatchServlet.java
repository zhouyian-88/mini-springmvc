package com.qfedu.mini.mvc.servlet;

import com.qfedu.mini.mvc.annotation.Controller;
import com.qfedu.mini.mvc.annotation.RequestMapping;
import com.qfedu.mini.mvc.util.MiniMVCUtils;
import jdk.nashorn.internal.ir.RuntimeNode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;

/**
 * @athor:zhouhaohui
 * @email:2873642764@qq.com
 * @desc:
 * @datetime:2022-11-30-20:15
 */
public class DispatchServlet extends HttpServlet {
   private Properties p = new Properties();
    private MiniMVCUtils util = new MiniMVCUtils();
    private static final String CONTEXT_CONFIG_KEY = "ContextConfigLocation";
    private static final String BASE_PACKAGE = "basePackage";
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filePath = this.getInitParameter(CONTEXT_CONFIG_KEY);
        if(filePath.startsWith("classpath:")){
            filePath=filePath.replace("classpath:", "");//得到配置文件的名称
        }
        InputStream is = util.getClassLoader().getResourceAsStream(filePath);//读取配置文件
        p.load(is);//加载配置文件到properties
       String basePackage =  p.getProperty(BASE_PACKAGE );//得到文件中的内容
        Set<Class<?>> classSet = util.getClassSetToPackage(basePackage);
        //遍历classSet
        classSet.stream().forEach(clazz->{
            //首先判断是否有controller注解
            Controller controllerAnnotation = clazz.getAnnotation(Controller.class);
            if(controllerAnnotation!=null){
                //得到controller中被requestMapping标注的方法
                Method[] methods = clazz.getDeclaredMethods();
                Arrays.stream(methods).forEach(method ->{
                    RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                    if(mapping!=null){
                        //获取请求路径
                        String uri = req.getRequestURI();
                        uri = uri.replace(req.getContextPath(), "");

                        String value = mapping.value();
                        if(uri.equals(value)){
                            //执行当前方法
                            try {
                                method.invoke(clazz.newInstance(),req,resp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }

                });
            }
        });
    }
}
