package com.edesappux.linternaux.Helpers;

import java.util.Timer;
import java.util.TimerTask;

public class SimpleIntervalTime {

    private final Timer timer;
    private TimerTask task;
    private long interval;
    private boolean isRunning;

    public SimpleIntervalTime() {
        timer = new Timer();
        isRunning = false;
    }

    public void setTimeout(Runnable runnable, long delay, boolean cancelTimer) {
        task = new TimerTask() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    e.printStackTrace();
                    // Considera registrar la excepción o manejarla de manera más específica según tus necesidades.
                } finally {
                    if (cancelTimer && isRunning) {
                        timer.cancel();
                        isRunning = false;
                    }
                }
            }
        };
        timer.schedule(task, delay);
        isRunning = true;
    }

    public void setInterval(Runnable runnable, long interval) {
        this.interval = interval;
        task = new TimerTask() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    e.printStackTrace();
                    // Considera registrar la excepción o manejarla de manera más específica según tus necesidades.
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, interval); // Comienza inmediatamente y luego repite cada intervalo
        isRunning = true;
    }

    public void clearInterval() {
        if (task != null) {
            task.cancel();
            isRunning = false;
        }
    }

    public void clearTimeout() {
        if (task != null) {
            task.cancel();
            isRunning = false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void cancel() {
        if (isRunning) {
            timer.cancel();
            isRunning = false;
        }
    }
}
