package com.db.backend.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledTaskExample {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> System.out.println("Executando uma tarefa agendada.");
        executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS); // Agenda a tarefa para execução a cada 1 segundo
        try {
            TimeUnit.SECONDS.sleep(5); // O programa principal dorme por 5 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown(); // Desliga o executor após a execução da tarefa
    }
}
