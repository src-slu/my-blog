package com.ls.springcloud.service;

import com.ls.springcloud.pojo.Email;

import java.util.List;

/**
 * @ClassName EmailService
 * @Description
 * @Author lushuai
 * @Date 2019/11/11 10:32
 */
public interface EmailService {

    List<Email> receiveEmail();
}
