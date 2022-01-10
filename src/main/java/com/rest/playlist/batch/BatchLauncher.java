package com.rest.playlist.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Component;

@Component
public class BatchLauncher {

    private JobLauncher jobLauncher;
    private Job job;

    public BatchLauncher(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    public BatchStatus run() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters parameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, parameters);
        return jobExecution.getStatus();
    }
}
