package com.emailq.emailq.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.emailq.emailq.model.EmailTemplate;
import com.emailq.emailq.repository.EmailTemplateRespository;

@Service
public class EmailTemplateService {
   private final EmailTemplateRespository templateRepository;

    public EmailTemplateService(EmailTemplateRespository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public EmailTemplate createTemplate(EmailTemplate template) {
        return templateRepository.save(template);
    }


    public EmailTemplate getTemplateById(UUID id) {
        return templateRepository.findById(id).orElseThrow(() -> new RuntimeException("Template not found"));
    }

    public EmailTemplate getTemplateByName(String name) {
        return templateRepository.findByName(name).orElseThrow(() -> new RuntimeException("Template not found"));
    }

    public EmailTemplate updateTemplate(UUID id, EmailTemplate updatedTemplate) {
        EmailTemplate existingTemplate = getTemplateById(id);
        existingTemplate.setName(updatedTemplate.getName());
        existingTemplate.setSubject(updatedTemplate.getSubject());
        existingTemplate.setHtmlBody(updatedTemplate.getHtmlBody());
        return templateRepository.save(existingTemplate);
    }

    public void deleteTemplate(UUID id) {
        templateRepository.deleteById(id);
    }

    public List<EmailTemplate> getAllTemplates() {
        return templateRepository.findAll();
    }

    // Methods for creating, updating, deleting, and retrieving templates would go here
    
}
