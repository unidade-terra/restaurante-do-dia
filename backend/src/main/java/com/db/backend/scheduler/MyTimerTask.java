package com.db.backend.scheduler;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    public void run() {
        System.out.println("Executando uma tarefa agendada.");
    }

    public static void main(String[] args) {
        TimerTask timerTask = new MyTimerTask();
        Timer timer = new Timer(true);
        // Agenda a tarefa para executar de 10 em 10 secundos (10000 milissegundos)
        timer.scheduleAtFixedRate(timerTask, 0, 10000); // Inicia imediatamente e repete a cada 10 segundos
        // Loop para manter o programa em execução
        try {
            while (true) {
                Thread.sleep(1000); // Dorme em intervalos de 1 segundo para manter o programa rodando
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
