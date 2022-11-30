package com.qfedu.mini.mvc.util;

/**
 * @athor:zhouhaohui
 * @email:2873642764@qq.com
 * @desc:
 * @datetime:2022-11-30-19:26
 */


import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 这个类是用来通过指定的包，读取当前包下的所有类，包括子包中的类
 */
public class MiniMVCUtils {
    public ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    public Set<Class<?>> getClassSetToPackage(String basePackage){
       Set<Class<?>> classSet = new HashSet<>();
        //使用类加载器得到url地址
        URL url = getClassLoader().getResource(basePackage.replace(".", "/"));
        String path = url.getPath();
        System.out.println(path);
        //读取指定路径下所有的字节码文件
        loadClassSetToPath(classSet,path,basePackage);

        return classSet;
    }

    private void loadClassSetToPath(Set<Class<?>> classSet, String basePath, String basePackage) {
        File[] files = new File(basePath).listFiles(file -> {
            //这里只要目录或者文件
            return file.getName().endsWith(".class")||file.isDirectory();
        });
        //循环files数组
        Arrays.stream(files).forEach(file -> {
            if(file.isDirectory()){
                //文件夹
                //获取名称
               String fileName = file.getName();
               String newBasePath = basePath + "/" + fileName;
               String newBasePack = basePackage + "." + fileName;
               //重新执行loadClassSetToPath
                loadClassSetToPath(classSet,newBasePath, newBasePack);
            }else{
                //是class文件
                //获取类名
                String className = file.getName().replaceAll(".class", "");
                //得到类的全路径
                String realClassName = basePackage + "." + className;
                //创建字节码对象
                createClassObject(classSet,realClassName);



            }
        });

    }

    private void createClassObject(Set<Class<?>> classSet, String realClassName) {
        try {
            Class<?> aClass = Class.forName(realClassName);
            classSet.add(aClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MiniMVCUtils miniMVCCommons = new MiniMVCUtils();
        Set<Class<?>> classSetToPackage = miniMVCCommons.getClassSetToPackage("com.qfedu.mini.mvc.controller");
        System.out.println(classSetToPackage);
    }
}
