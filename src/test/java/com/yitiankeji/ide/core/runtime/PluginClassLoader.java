package com.yitiankeji.ide.core.runtime;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginClassLoader {

    public static void main(String[] args) throws Exception {
        String jarFilePath = "file:D:\\Workspace\\ide\\test\\target\\test-1.0-SNAPSHOT.jar";

        URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL(jarFilePath)});

        // 加载类
        Class<?> clazz = classLoader.loadClass("com.yitiankeji.ide.AAA");
        Object obj = clazz.getConstructor().newInstance();

        // 调用方法
        Method method = clazz.getMethod("print");
        method.invoke(obj);

        classLoader.close();
    }
}
