package com.emailq.emailq.service;

import org.springframework.stereotype.Service;

import com.emailq.emailq.model.EmailJobMessage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailSenderService {

    public void send(EmailJobMessage message) {
        // SendGrid integration comes in Phase 5
        log.info("Sending email to: {} | subject: {}", message.getRecipient(), message.getSubject());
    }
}