package com.ls.springcloud.service.impl;

import com.ls.springcloud.exception.MyException;
import com.ls.springcloud.exception.MyExceptionResolver;
import com.ls.springcloud.mapper.UserMapper;
import com.ls.springcloud.pojo.User;
import com.ls.springcloud.service.UserService;
import com.ls.springcloud.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName userServiceImpl
 * @Description
 * @Author lushuai
 * @Date 2019/11/13 15:17
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 注册
     *
     * @param user
     */
    @Override
    public boolean registry(User user) {
        return userMapper.save(user);
    }

    /**
     * 登陆(获取密码)
     *
     * @param email
     */
    public String getPassword(String email) {

        if(redisUtils.get(email+"_pass") != null){
            return (String)redisUtils.get(email+"_pass");
        }
        String password = userMapper.getPassword(email);
        /**
         * 第一次获取密码后放入redis缓存
         */
        redisUtils.set(email+"_pass", password);
        return password;
    }

    /**
     * 登出
     *
     * @param email
     */
    public void logout(String email) {
//        Item.removeSession("user");
        /**
         * 登出后，删除redis缓存
         */
        if(redisUtils.get(email) != null){
            redisUtils.deleteKey(email);
        }
    }

    /**
     * 注销
     *
     * @param email
     * @return
     */
    public boolean remove(String email) {

        /**
         * 删除redis用户信息后，再删除数据库数据
         */
        if(redisUtils.get(email+"*") != null){
            redisUtils.deleteKey(email+"_info");
        }
        return userMapper.remove(email);
    }

//    /**
//     * 注销成功后，删除关联
//     *
//     * @param telephone
//     * @return
//     */
//    public boolean deleteRelationFromUserAndEmail(long telephone) {
//        return userMapper.deleteRelationFromUserAndEmail(telephone);
//    }

    /**
     * 获取详细信息
     *
     * @param email
     * @return
     */
    @Override
    public User getUserInfo(String email) throws MyException {
        try{
            return userMapper.getUserInfo(email);
        }catch (Exception e){
            throw new MyExceptionResolver().printException("获取用户信息失败： {}", e);
        }
    }

    /**
     * 通过邮件激活用户
     *
     * @param email
     * @return
     */
    @Override
    public int activeUser(String email) {
        return userMapper.activeUser(email);
    }

    /**
     * 查看用户是否是激活状态
     * @param email
     * @return
     */
     public int isActive(String email){
        return userMapper.getStatus(email);
     }

    /**
     * 修改密码
     * @param email
     * @return
     */
     public int modifyPassword(String email, String password){
         User user = new User();
         user.setEmail(email);
         user.setPassword(password);
         int updatePassword = userMapper.updatePassword(user);

         if(updatePassword != 0){
             redisUtils.set(email+"_pass", password);
         }
         return updatePassword;
     }

    /**
     * 根据email获取用户信息
     * @param email
     * @return
     * @throws MyException
     */
     @Override
     public User selectUserByEmail(String email) throws MyException {
         User userInfo = null;
         try {
             if (redisUtils.get(email + "_info") != null) {
                 return (User) redisUtils.get(email + "_info");
             }
             userInfo = userMapper.getUserInfo(email);
             if(userInfo != null){
                 redisUtils.set(email + "_info", userInfo);
             }
         }catch (Exception e){
            throw new MyExceptionResolver().printException("查找用户发生异常: %s", e);
         }
         return userInfo;
     }
}
