package com.ls.springcloud.service;

import com.ls.springcloud.exception.MyException;
import com.ls.springcloud.pojo.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName UserService
 * @Description
 * @Author lushuai
 * @Date 2019/11/13 15:04
 */
@Transactional
public interface UserService {

    /**
     * 注册
     * @param user
     */
    boolean registry(User user);

    /**
     * 登陆(获取密码)
     * @param email
     */
    String getPassword(String email);

    /**
     * 登出
     * @param email
     */
    void logout(String email);

    /**
     * 注销
     * @param email
     * @return
     */
    boolean remove(String email);

//    /**
//     * 删除成功后，删除关联
//     * @param email
//     * @return
//     */
//    boolean deleteRelationFromUserAndEmail(long telephone);

    /**
     * 获取详细信息
     * @param email
     * @return
     */
    User getUserInfo(String email) throws MyException;

    /**
     * 通过邮件激活用户
     * @param email
     * @return
     */
    int activeUser(String email);

    /**
     * 查看用户是否是激活状态
     * @param email
     * @return
     */
    int isActive(String email);

    /**
     * 修改密码
     * @param email
     * @return
     */
    int modifyPassword(String email, String password);

    /**
     * 通过email获取用户
     * @param email
     * @return
     */
    User selectUserByEmail(String email) throws MyException;
}
