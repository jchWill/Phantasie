package com.phantasie.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
    public String sendMail(String where);
}
