package com.emailq.emailq.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emailq.emailq.model.EmailTemplate;
import com.emailq.emailq.service.EmailTemplateService;

@RestController
@RequestMapping("/api/templates")
public class EmailTemplateController {
    private final EmailTemplateService templateService;

    public EmailTemplateController(EmailTemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping
    public ResponseEntity<EmailTemplate> createTemplate(@RequestBody EmailTemplate template) {
        EmailTemplate createdTemplate = templateService.createTemplate(template);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTemplate); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailTemplate> getTemplateById(@PathVariable UUID id) {
        EmailTemplate template = templateService.getTemplateById(id);
        return ResponseEntity.ok(template); 
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<EmailTemplate> getTemplateByName(@PathVariable String name) {
        EmailTemplate template = templateService.getTemplateByName(name);
        return ResponseEntity.ok(template);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailTemplate> updateTemplate(@PathVariable UUID id, @RequestBody EmailTemplate updatedTemplate) {
        EmailTemplate template = templateService.updateTemplate(id, updatedTemplate);
        return ResponseEntity.ok(template);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable UUID id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllTemplates() {
        return ResponseEntity.ok(templateService.getAllTemplates());
    }
    
}
