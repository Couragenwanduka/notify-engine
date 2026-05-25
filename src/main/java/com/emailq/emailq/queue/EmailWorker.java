package com.emailq.emailq.queue;

import com.emailq.emailq.enums.JobStatus;
import com.emailq.emailq.model.EmailJobMessage;
import com.emailq.emailq.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailWorker {

    private static final int MAX_RETRIES = 3;

    private final JobQueueService jobQueueService;
    private final EmailSenderService emailSenderService;

    @Scheduled(fixedDelay = 2000) // runs every 2 seconds
    public void processJobs() {
        EmailJobMessage message = jobQueueService.dequeue();

        if (message == null) return; // nothing in queue

        log.info("Processing job: {}", message.getJobId());

        try {
            emailSenderService.send(message);
            message.setStatus(JobStatus.SENT);
            log.info("Job sent successfully: {}", message.getJobId());

        } catch (Exception e) {
            log.error("Job failed: {} | reason: {}", message.getJobId(), e.getMessage());
            int retries = message.getRetryCount() + 1;
            message.setRetryCount(retries);

            if (retries >= MAX_RETRIES) {
                message.setStatus(JobStatus.DEAD);
                jobQueueService.sendToDeadLetterQueue(message);
                log.warn("Job moved to dead letter queue after {} retries: {}", retries, message.getJobId());
            } else {
                message.setStatus(JobStatus.PENDING);
                jobQueueService.enqueue(message); // re-queue for retry
                log.info("Job re-queued for retry #{}: {}", retries, message.getJobId());
            }
        }
    }
}