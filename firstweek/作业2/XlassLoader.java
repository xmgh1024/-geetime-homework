package com.example.demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Administrator
 * @version 1.0.0
 * description:
 * @create 2021/8/8 16:36
 */
public class XlassLoader extends ClassLoader {


    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        String className = "Hello";
        String methodName = "hello";
        XlassLoader xlassLoader = new XlassLoader();
        Class<?> aClass = xlassLoader.loadClass(className);
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (int i = 0; i <declaredMethods.length ; i++) {
            System.out.println("方法"+(i+1)+declaredMethods[i].getName());
        }
        Object instance = aClass.getDeclaredConstructor().newInstance();
        Method method = aClass.getMethod(methodName);
        method.invoke(instance);

    }

    @Override
    protected Class<?> findClass(String name){

        byte[] targetBytes = null;
        InputStream in = null;
        try {
            in = XlassLoader.class.getClassLoader().getResourceAsStream("Hello.xlass");
            int length ;
            int available = in.available();
           byte[] source =  new byte[available];
            while ((length = in.read(source)) != -1) {
//                System.out.println(new String(source, 0, length));
            }
            targetBytes =  decode(source);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (in !=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  defineClass(name,targetBytes,0,targetBytes.length);
    }

    
    private byte[] decode(byte[] source) {
        byte[] result  = new byte[source.length];
        for (int i = 0; i <source.length ; i++) {
            result[i] = (byte) (255 -source[i]);
        }
        return result;
    }
}
