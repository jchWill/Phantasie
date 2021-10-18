package com.phantasie.demo.serviceImpl;

import com.phantasie.demo.controller.LoginController;
import com.phantasie.demo.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

import java.util.Random;

public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.fromMail.sender}")
    private String sender;// 发送者

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public String sendMail(String where){
        SimpleMailMessage message = new SimpleMailMessage();
        String code = VerifyCode(10);    //随机数生成10位验证码
        message.setFrom(sender);
        message.setTo(where);
        message.setSubject("Phantasie验证");// 标题
        message.setText("【Phantasie】你的验证码为："+code+"，有效时间为5分钟(若不是本人操作，可忽略该条邮件)");// 内容

        try {
            javaMailSender.send(message);
            logger.info("文本邮件发送成功！");
            LoginController.saveCode(code);
            return "success";
        }catch (MailSendException e){
            logger.error("目标邮箱不存在");
            return "false";
        } catch (Exception e) {
            logger.error("文本邮件发送异常！", e);
            return "failure";
        }
    }

    private String VerifyCode(int n){
        Random r = new Random();
        StringBuffer sb =new StringBuffer();
        for(int i = 0;i < n;i ++){
            int ran1 = r.nextInt(10);
            sb.append(String.valueOf(ran1));
        }
//        System.out.println(sb);
        return sb.toString();
    }
}
