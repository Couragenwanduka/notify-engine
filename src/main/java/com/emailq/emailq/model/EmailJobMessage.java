package com.emailq.emailq.model;

import com.emailq.emailq.enums.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailJobMessage implements Serializable {

    private String jobId;         // links back to EmailJob in PostgreSQL
    private String recipient;
    private String subject;
    private String body;
    private String templateId;
    private Map<String, String> variables;

    @Builder.Default
    private JobStatus status = JobStatus.PENDING;

    @Builder.Default
    private int retryCount = 0;

    @Builder.Default
    private LocalDateTime queuedAt = LocalDateTime.now();
}