package com.db.backend.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {
    @Scheduled(fixedDelay = 1000 * 5)
    public void scheduleFixedDelayTask() {
        System.out.println("Executando uma tarefa agendada com fixedDelay");
    }
}
