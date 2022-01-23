package com.ls.springcloud.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName User
 * @Description
 * @Author lushuai
 * @Date 2019/11/13 14:52
 */
@Data
public class User {

//    private String email;
    private String name;
    private String password;
    private String email;
    private Date registryDate;
    private Integer activeStatus;

}
