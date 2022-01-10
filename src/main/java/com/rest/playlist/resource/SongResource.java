package com.rest.playlist.resource;

import com.rest.playlist.batch.BatchLauncher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/songs")
public class SongResource {

    private BatchLauncher batchLauncher;

    public SongResource(BatchLauncher batchLauncher) {
        this.batchLauncher = batchLauncher;
    }


    @GetMapping
    public BatchStatus loadBatch() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        log.info("Batch demarré à la demande");
        return batchLauncher.run();
    }

}
