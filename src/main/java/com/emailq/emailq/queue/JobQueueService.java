package com.emailq.emailq.queue;

import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.emailq.emailq.model.EmailJobMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class JobQueueService {

    private static final String QUEUE_NAME = "emailq:jobs";
    private static final String DEAD_LETTER_QUEUE = "emailq:dead";
  
    private final RedissonClient redissonClient;

     public void enqueue(EmailJobMessage message) {
        RQueue<EmailJobMessage> queue = redissonClient.getQueue(QUEUE_NAME);
        queue.add(message);
        log.info("Job enqueued: {}", message.getJobId());
    }

     public void sendToDeadLetterQueue(EmailJobMessage message) {
        RQueue<EmailJobMessage> deadQueue = redissonClient.getQueue(DEAD_LETTER_QUEUE);
        deadQueue.add(message);
        log.warn("Job moved to dead letter queue: {}", message.getJobId());
    }

     public EmailJobMessage dequeue() {
        RQueue<EmailJobMessage> queue = redissonClient.getQueue(QUEUE_NAME);
        return queue.poll();
    }

     public int getQueueSize() {
        return redissonClient.getQueue(QUEUE_NAME).size();
    }


    
}
