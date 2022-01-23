package com.ls.springcloud.mq;

import com.ls.springcloud.pojo.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @ClassName MailUtils
 * @Description
 * @Author lushuai
 * @Date 2019/11/19 14:50
 */
@Component
@Slf4j
public class MailUtils {

    @Value("${spring.mail.from}")
    private String from;

    @Autowired
    private JavaMailSender sender;

    /**
     * 普通邮件
     * @param mail
     * @return
     */
    public boolean send(Email mail){
        String to = mail.getTo();
        String title = mail.getTitle();
        String content = mail.getContent();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(title);
        mailMessage.setText(content);

        try {
            sender.send(mailMessage);
            log.info("邮件发送成功");
            return true;
        }catch (Exception e){
            log.error("邮件发送失败 [{}]", e.getMessage());
            return false;
        }
    }

    /**
     * 带有附件的邮件
     * @param email
     * @param file
     * @return
     */
    public boolean sendWithFile(Email email, File file){
        String to = email.getTo();
        String title = email.getTitle();
        String content = email.getContent();

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(title);
            helper.setText(content);

            FileSystemResource resource = new FileSystemResource(file);
            String name = file.getName();
            helper.addAttachment(name, resource);
            sender.send(message);
            log.info("附件邮件发送成功");
            return true;
        } catch (MessagingException e) {
            log.error("附件邮件发送失败, to: {}, title: {}", to, title, e);
            return false;
        }
    }
}
