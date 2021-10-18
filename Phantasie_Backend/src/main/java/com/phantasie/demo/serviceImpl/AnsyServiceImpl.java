package com.phantasie.demo.serviceImpl;

import com.phantasie.demo.service.AnsyService;
import com.phantasie.demo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

public class AnsyServiceImpl implements AnsyService {
    @Autowired
    MailService mailService;

    @Async
    @Override
    public String send(String where){
        return mailService.sendMail(where);
    }

}
