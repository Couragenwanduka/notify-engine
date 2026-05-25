package com.emailq.emailq.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emailq.emailq.model.EmailTemplate;

public interface EmailTemplateRespository extends JpaRepository<EmailTemplate, UUID> {
    Optional<EmailTemplate> findByName(String name);
    
}
