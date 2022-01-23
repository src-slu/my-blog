package com.ls.springcloud.controller;

import com.ls.springcloud.base.*;
import com.ls.springcloud.exception.MyException;
import com.ls.springcloud.exception.MyExceptionResolver;
import com.ls.springcloud.pojo.Email;
import com.ls.springcloud.pojo.User;
import com.ls.springcloud.utils.JsonUtils;
import com.ls.springcloud.utils.NetUtils;
import com.ls.springcloud.utils.RedisUtils;
import com.ls.springcloud.service.MsgLogService;
import com.ls.springcloud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @ClassName UserController
 * @Description
 * @Author lushuai
 * @Date 2019/11/13 14:58
 */
@Slf4j
@RestController
@RequestMapping("user")
@PropertySource("/static/config/email.properties")
public class UserController extends BaseController {

    private static final long serialVersionUID = 3488921930653221685L;

    private int clickCount = 0;
    @Autowired
    private UserService userService;

    @Value("${spring.email.from}")
    private String from;

    @Value("${server.port}")
    private String port;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MsgLogService msgLogService;

//    private static final String KEY_PASS = "SPRING-CLOUD@EMAIL+PASS_KEY";

//    private static final char DEFAULT_CHARACTER = '#';

    /**
     * 注册
     */
    @RequestMapping("/registry")
    public ResultSet registry(HttpServletRequest request) throws MyException {
        User user = new User();
        try {
            Item item = this.getItem();

//            user.setEmail(data.get("email").toString());
            String email = item.getStringColumn("email");
            user.setName(item.getStringColumn("name"));
            /**
             * 密码加密方式 key + # + email + # + 密码
             * 密码为前端使用md5加密后的加密字符串，
             */
            user.setPassword(item.getStringColumn("password"));
            user.setEmail(email);
            user.setRegistryDate(new Date());
            user.setActiveStatus(0);
            /**
             * 判断用户是否已经存在
             */
            if (userService.selectUserByEmail(email) != null) {
//                return Response.okResponse("用户已存在");
                return Response.response(1111, "用户已存在");
            }
            if (!userService.registry(user)) {
                return Response.okResponse("注册失败");
            }
            /**
             * 调用邮件服务发送邮件
             */
            buildEmailMessage(item, request);
        } catch (Exception e) {
            throw new MyExceptionResolver().printException("注册发生异常", e);
//            return Response.errorResponse("注册失败");
        }
        return Response.okResponse(String.format("账号[ %s ]注册成功，请留意邮件，进行账号激活", user.getEmail()));
    }

    /**
     * 创建消息通知
     *
     * @param item
     */
    private void buildEmailMessage(Item item, HttpServletRequest request) {
        Email email = new Email();
        email.setFrom(from);
        email.setTitle("注册通知");
        email.setTo(item.getStringColumn("email"));
        String url = "http://" + NetUtils.getRealIpAdd(request) + ":" + port
                + "/user/active/" + item.getStringColumn("email")
                + "/" + System.currentTimeMillis();
        email.setContent("请通过点击以下链接进行账号激活,链接在30分钟内有效 \n" + url);
        /**
         * 通知形式使用发送邮件链接进行激活
         * (更改用户状态0 未激活 1 已激活)
         */
        redisUtils.set(email.getTo(), JsonUtils.objToString(email), 30000);
        msgLogService.send(email);
    }

    /**
     * 登陆
     * 使用email进行登陆
     */
    @RequestMapping("/login")
    public ResultSet login() {
        String tokenId;
        try {
            Item item = this.getItem();
            String email = item.getStringColumn("email");
            String password = item.getStringColumn("password");
//            String tokenId = item.getStringColumn("tokenId");

            int active = userService.isActive(email);
            if (active == 0) {
                return Response.errorResponse("用户未激活,请前往邮箱激活再登陆");
            }
            String dbPassword = userService.getPassword(email);

            /**
             * 用户名密码正确，将email
             */
            if (!password.equals(dbPassword)) {
                log.warn("登陆失败，用户名/密码不匹配");
                return Response.errorResponse("登陆失败，用户名/密码不匹配");
            }

            /**
             * 登陆成功后，生成tokenId
             * 将用户token信息存入redis
             */
            //TODO 生成tokenId
            tokenId = "";
            redisUtils.set(email + "_tokenId", tokenId, 60*24*7);
            /**
             * 登陆成功后，将该用户密码和用户名存入redis缓存,存在30分钟
             */
//            redisUtils.set(email + "_pass", password, 60*24*7);
//            redisUtils.set(email, email);
//            Item.setSession("user", email);
        } catch (Exception e) {
            log.warn("登陆发生异常 [{}]", e.getMessage());
            return Response.errorResponse("登陆失败: " + e.getMessage());
        }
        // 登陆成功后，返回tokenId给前端用作校验后续
        return Response.response(1001,"登陆成功", tokenId);
    }

    /**
     * 注销
     */
    @RequestMapping("/cancel")
    public ResultSet cancel() {
        try {
            Item item = this.getItem();
            /**
             * 注销成功后，直接退出系统
             */
            if (userService.remove(item.getStringColumn("email"))) {
                userService.logout(item.getStringColumn("email"));
            } else {
                return Response.errorResponse("注销失败");
            }
        } catch (Exception e) {
            log.warn("注销用户发生异常 [{}]", e.getMessage());
            return Response.errorResponse("注销失败：" + e.getMessage());
        }
        return Response.okResponse("注销成功");
    }

    /**
     * 登出
     */
    @RequestMapping("/logout")
    public void logout() {
        try {
            Item item = this.getItem();
            userService.logout(item.getStringColumn("email"));
        } catch (Exception e) {
            log.error("退出登陆失败 [{}]", e.getMessage());
        }
    }

    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping("/getInfo")
    public ResultSet getInfo() {
        User userInfo = null;
        try {
//            Map<String, Object> pram = Item.getPram();
            Item item = this.getItem();
            userInfo = userService.getUserInfo(item.getStringColumn("email"));
        } catch (Exception e) {
            log.error("查看详情失败 [{}]", e.getMessage());
        }
        return Response.okResponse(userInfo);
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping("/updatePass")
    public ResultSet updatePassword() {
        Item item = this.getItem();
        String email = item.getStringColumn("email");
        String oldPass = item.getStringColumn("oldPass");
        String newPass = item.getStringColumn("newPass");

        if (oldPass.equals(newPass)) {
            return Response.errorResponse("新密码不能与旧密码相同");
        }
        if (userService.modifyPassword(email, newPass) == 0) {
            return Response.errorResponse("密码修改失败");
        }
        return Response.okResponse();
    }

    /**
     * 通过邮件激活用户
     *
     * @param email
     * @return
     */
    @RequestMapping("/active/{email}/{timestamp}")
    public ModelAndView activeUser(@PathVariable String email, @PathVariable long timestamp) {
        ModelAndView mav = new ModelAndView();
        String result;
        long nowTimestamp = System.currentTimeMillis();
        /**
         * 链接时间超过30分钟，则失效，删除注册的用户信息
         */
        if ((nowTimestamp - timestamp) / 60000 > 30) {
            userService.remove(email);
            result = "链接已失效，请重新注册";
//            return "激活链接失效，请重新注册";
        }else{
            int activeUser = userService.activeUser(email);
            if(activeUser == 0){
                result = "账号激活失败，请重新注册";
            }else {
                result = "账号激活成功";
            }
        }
        mav.addObject("msg", result);
        mav.setViewName("notice");
        return mav;
    }

}
