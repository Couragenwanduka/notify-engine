package com.emailq.emailq.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emailq.emailq.enums.JobStatus;
import com.emailq.emailq.model.EmailJob;
import com.emailq.emailq.service.EmailJobService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailJobController {

    private final EmailJobService emailJobService;

    // Send a single email
    @PostMapping("/send")
    public ResponseEntity<EmailJob> sendEmail(@RequestBody Map<String, Object> request) {
        String recipient = (String) request.get("recipient");
        String subject = (String) request.get("subject");
        String body = (String) request.get("body");
        String templateId = (String) request.get("templateId");
        Map<String, String> variables = (Map<String, String>) request.get("variables");

        EmailJob job = emailJobService.createJob(recipient, subject, body, templateId, variables);
        return ResponseEntity.ok(job);
    }

    // Get all jobs
    @GetMapping
    public ResponseEntity<List<EmailJob>> getAllJobs() {
        return ResponseEntity.ok(emailJobService.getAllJobs());
    }

    // Get job by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmailJob> getJobById(@PathVariable String id) {
        return ResponseEntity.ok(emailJobService.getJobById(id));
    }

    // Get jobs by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<EmailJob>> getJobsByStatus(@PathVariable JobStatus status) {
        return ResponseEntity.ok(emailJobService.getJobsByStatus(status));
    }
}