package com.ls.springcloud.controller;

import com.ls.springcloud.base.BaseController;
import com.ls.springcloud.base.Item;
import com.sun.net.ssl.internal.ssl.Provider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.security.Security;
import java.util.Properties;

/**
 * @ClassName Emailcontroller
 * @Description
 * @Author lushuai
 * @Date 2019/11/11 10:39
 */
@RestController
@RequestMapping("email")
@Slf4j
public class EmailController extends BaseController {
    private static final long serialVersionUID = 3838091119431672747L;

    /**
     * 接收邮件
     */
    @RequestMapping("/receive")
    public void receiveEmails() {

        Folder folder = null;
        Store store = null;
        Item item = new Item();
        try {
            /**
             * 连接服务器会话信息
             */
            Properties properties = new Properties();
            properties.setProperty("mail.store.protocol", "imap");
            properties.setProperty("mail.imap.host", "imap.163.com");
            properties.setProperty("mail.imap.port", "143");
            /**
             * 创建session
             */
            Session session = Session.getInstance(properties);
            /**
             * 创建store协议
             */
            store = session.getStore("imap");
            store.connect("ls75650@163.com", "Ls320324");

            /**
             * 收取邮箱
             */
            folder = store.getFolder("INBOX");
            /** 以读写模式打开收件箱 */
            folder.open(Folder.READ_WRITE);

            /**
             * 获取邮箱邮件列表
             */
            Message[] messages = folder.getMessages();

            // 打印不同状态的邮件数量
            System.out.println("收件箱中共" + messages.length + "封邮件!");
            System.out.println("收件箱中共" + folder.getUnreadMessageCount() + "封未读邮件!");
            System.out.println("收件箱中共" + folder.getNewMessageCount() + "封新邮件!");
            System.out.println("收件箱中共" + folder.getDeletedMessageCount() + "封已删除邮件!");

            /**
             * 解析邮件
             */
            int messageCount = folder.getMessageCount();
            System.out.println("收件箱中共" + messageCount + "封邮件!");
            for (int i = 0; i < messageCount; i++) {

            }
            int i = 1;
            for (Message message : messages) {
                System.out.println("======================= 第" + i++ + "封邮件 ==================================");

//                Message message = messages[i];
                Address[] from = message.getFrom();
                for (Address address : from) {
                    String sender = address.toString().trim();
                    String substring = sender.substring(sender.indexOf("<"), sender.indexOf(">"));
                    item.put("from", substring);
                    System.out.println("发件人: " + substring);
                }

                item.put("title", message.getSubject());
                item.put("content", message.getContent());
                item.put("sentDate", message.getSentDate());
                item.put("receiveDate", message.getReceivedDate());
//                System.out.println("标题: " + message.getSubject());
//                System.out.println("内容: " + message.getContent());
//                System.out.println("发送时间: " + message.getSentDate());
//                System.out.println("接收时间: " + message.getReceivedDate());
            }
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                folder.close(false);
                store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
//        return Response.okResponse(item);
    }

    @RequestMapping("/receiveBySSL")
    public void receiveBySSL(){
        Session session = null;
        Store store = null;
        Folder folder = null;

        try {
            Security.addProvider(new Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

            Properties props = new Properties();
            props.setProperty("mail.imap.host", "imap.partner.outlook.cn");
            props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.imap.socketFactory.fallback", "false");
            props.setProperty("mail.imap.port", "993");
            props.setProperty("mail.imap.socketFactory.port", "993");
            props.setProperty("mail.imap.auth", "true");

            /**
             * 创建session会话
             */
            session = Session.getInstance(props);
            URLName url = new URLName(
                    "imap","imap.partner.outlook.cn",993,null,
                    "ls75650@163.com","Ls320324");
            /**
             * 获取仓库并连接
             */
            store = session.getStore(url);
            store.connect();
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);

            FetchProfile profile = new FetchProfile();
            profile.add(FetchProfile.Item.ENVELOPE);

            /**
             * false表示未读
             */
            FlagTerm flagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);

            Message[] messages = folder.search(flagTerm);
            folder.fetch(messages, profile);
            int length = messages.length;
            log.info("收件箱中共有{}封邮件", length);

            /**
             * 遍历收件箱、垃圾箱等名称
             */
            Folder defaultFolder = store.getDefaultFolder();
            Folder[] folders = defaultFolder.list();

            for(int i=0; i<folders.length; i++){
                log.info("{}", folders[i].getName());
            }

            /**
             * 遍历邮箱邮件
             */
            for(int i=0; i<length; i++){
                String from = MimeUtility.decodeText(messages[i].getFrom()[0].toString());
                InternetAddress address = new InternetAddress(from);
                log.info("发件人: {}", address);
                log.info("主题: {}", messages[i].getSubject());
                log.info("内容: {}", messages[i].getContent());
                log.info("发送时间: {}", messages[i].getSentDate());
                log.info("收件时间: {}", messages[i].getReceivedDate());
                messages[i].setFlag(Flags.Flag.SEEN, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(folder != null){
                    folder.close(false);
                }
                if(store != null){
                    store.close();
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
