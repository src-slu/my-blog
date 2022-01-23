package com.ls.springcloud.utils;

import java.util.regex.Pattern;

/**
 * @ClassName RegularUtils
 * @Description
 * @Author lushuai
 * @Date 2019/11/15 9:51
 */
public enum Regulars {

    REGULAR_PASSWORD("^(?![a-zA-Z]+$)(?![a-zA-Z]+[\\d]+$)[a-zA-Z][\\d\\S]{6,20}$"),

    REGULAR_TELEPHONE("^1(3[\\d]|4[5-9]|5[0-3,5-9]|6[2,5,6,7]|7[0-8]|8[\\d]|9[1,3,5,8,9])\\d{8}$"),

    REGULAR_EMAIL("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}");

    Regulars(String s) {
    }

    public static void main(String[] args) {
        String email = "lsf@163.com";
        boolean matches = Pattern.matches(String.valueOf(REGULAR_EMAIL), email);
        System.out.println(matches);

    }
}
