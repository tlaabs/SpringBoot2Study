package com.community.batch.jobs.inactive.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.stereotype.Component;

@Slf4j //로그 객체 따로 생성할 필요가 없다.
@Component //빈 등록
public class InactiveStepListener {
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        log.info("Before Job");
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        log.info("After Job");
    }
}
