package com.michaelmiklavcic;

import java.util.concurrent.*;

public class Factory {

    private WorkManager workManager;
    private int producers;
    private int consumers;
    private ExecutorService service;

    public Factory(WorkManager workManager, int producers, int consumers) {
        this.workManager = workManager;
        this.producers = producers;
        this.consumers = consumers;
        service = Executors.newFixedThreadPool(producers + consumers);
    }

    public void run() {
        BlockingQueue<Work> queue = new ArrayBlockingQueue<Work>(100);
        for (int i = 0; i < consumers; i++) {
            service.submit(new Consumer(queue, workManager));
        }
        for (int i = 0; i < producers; i++) {
            service.submit(new Producer(queue, workManager));
        }
    }

    public void stop() {
        service.shutdown();
        workManager.closeShop();
    }

}
