package com.michaelmiklavcic;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private BlockingQueue<Work> todo;
    private WorkManager manager;

    public Consumer(BlockingQueue<Work> todo, WorkManager workManager) {
        this.todo = todo;
        this.manager = workManager;
    }

    @Override
    public void run() {
        while (manager.saysConsume()) {
            Work work;
            try {
                work = todo.take();
            } catch (InterruptedException e) {
                throw new RuntimeException("Something broke?", e);
            }
            System.out.println("Did some work!-" + work.getID());
            manager.report();
        }
    }

}
