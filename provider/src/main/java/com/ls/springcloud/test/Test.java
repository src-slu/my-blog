package com.ls.springcloud.test;

import com.ls.springcloud.pojo.User;
import com.ls.springcloud.utils.Utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName Test
 * @Description
 * @Author lushuai
 * @Date 2020/4/13 10:19
 */
public class Test {

    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//        int n = 11;
//        System.out.println("11 >>> 1 : " + (n |= 11 >>> 1));
//        System.out.println("11 >>> 2 : " + (n |= 11 >>> 2));
//        System.out.println("11 >>> 4 : " + (n |= 11 >>> 4));
//        System.out.println("11 >>> 8 : " + (n |= 11 >>> 8));
//        System.out.println("11 >>> 16 : " + (n |= 11 >>> 16));
//
//        System.out.println((n < 0) ? 1 : (n >= 10000) ? 10000 : n + 1);


//        File file = new File("E:\\temp\\text.js");
//        System.out.println(file.getPath());
//        System.out.println(file.getAbsolutePath());
//        System.out.println(file.getCanonicalPath());


        String[] methods = Utils.getAllGetSet(User.class).split(",");
        ;
        for (int i = 0; i < methods.length; i++) {
            System.out.println(methods[i]);
        }


    }
}
