package com.ls.springcloud.mapper;

import com.ls.springcloud.pojo.Email;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName EmailMapper
 * @Description
 * @Author lushuai
 * @Date 2019/11/27 15:49
 */
@Repository
public interface EmailMapper {

    List<Email> receiveEmails();
}
