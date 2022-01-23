package com.ls.springcloud.mapper;

import com.ls.springcloud.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UserMapper
 * @Description
 * @Author lushuai
 * @Date 2019/11/14 8:53
 */
@Repository
public interface UserMapper {
    /**
     * 注册（保存用户信息）
     * @param user
     * @return
     */
    boolean save(User user);

    /**
     * 根据email获取密码
     * @param email
     * @return
     */
    String getPassword(@Param("email") String email);

    /**
     * 注销用户
     * @return
     */
    boolean remove(@Param("email") String email);

//    /**
//     * 获取用户信息
//     * @param email
//     * @return
//     */
//    List<User> userInfo(@Param("email") String email);

//    /**
//     * 注销成功后，删除关联
//     * @param email
//     * @return
//     */
//    boolean deleteRelationFromUserAndEmail(@Param("email")String email);

    /**
     * 获取详细信息
     * @param email
     * @return
     */
    User getUserInfo(@Param("email") String email);

    /**
     * 通过邮件激活用户
     * @param email
     * @return
     */
    int activeUser(@Param("email") String email);

    /**
     * 获取用户状态
     * @param email
     * @return
     */
    int getStatus(@Param("email") String email);

    /**
     * 修改密码
     * @param user
     * @return
     */
    int updatePassword(User user);

}
