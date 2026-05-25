package com.emailq.emailq.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emailq.emailq.enums.JobStatus;
import com.emailq.emailq.model.EmailJob;


public interface EmailJobRepository  extends JpaRepository<EmailJob, UUID> {
    List<EmailJob> findByStatus(JobStatus status);
    Optional<EmailJob> findById(String id);
}
