package com.ls.springcloud.service.impl;

import com.ls.springcloud.mapper.EmailMapper;
import com.ls.springcloud.pojo.Email;
import com.ls.springcloud.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName EmailServiceImpl
 * @Description 接收邮件
 * @Author lushuai
 * @Date 2019/11/27 15:48
 */
@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailMapper emailMapper;

    @Override
    public List<Email> receiveEmail() {
        return emailMapper.receiveEmails();
    }
}
