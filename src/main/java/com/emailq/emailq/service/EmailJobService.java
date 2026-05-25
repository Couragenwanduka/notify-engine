package com.emailq.emailq.service;

import com.emailq.emailq.enums.JobStatus;
import com.emailq.emailq.model.EmailJob;
import com.emailq.emailq.model.EmailJobMessage;
import com.emailq.emailq.queue.JobQueueService;
import com.emailq.emailq.repository.EmailJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailJobService {

    private final EmailJobRepository emailJobRepository;
    private final JobQueueService jobQueueService;

    public EmailJob createJob(String recipient, String subject, String body, String templateId, Map<String, String> variables) {

        // 1. Save job to PostgreSQL
        EmailJob job = EmailJob.builder()
                .recipient(recipient)
                .subject(subject)
                .body(body)
                .templateId(templateId)
                .variables(variables)
                .status(JobStatus.PENDING)
                .build();

        emailJobRepository.save(job);
        log.info("Job saved to DB: {}", job.getId());

        // 2. Push message to Redis queue
        EmailJobMessage message = EmailJobMessage.builder()
                .jobId(job.getId())
                .recipient(recipient)
                .subject(subject)
                .body(body)
                .templateId(templateId)
                .variables(variables)
                .build();

        jobQueueService.enqueue(message);
        log.info("Job pushed to Redis queue: {}", job.getId());

        return job;
    }

    public List<EmailJob> getAllJobs() {
        return emailJobRepository.findAll();
    }

    public EmailJob getJobById(String id) {
        return emailJobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found: " + id));
    }

    public List<EmailJob> getJobsByStatus(JobStatus status) {
        return emailJobRepository.findByStatus(status);
    }
}