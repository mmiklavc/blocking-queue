package com.michaelmiklavcic;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private BlockingQueue<Work> todo;
    private WorkManager workManager;

    public Producer(BlockingQueue<Work> todo, WorkManager workManager) {
        this.todo = todo;
        this.workManager = workManager;
    }

    @Override
    public void run() {
        while (workManager.saysProduce()) {
            Work work = workManager.registerAssignment(new Work());
            System.out.println("Making work-" + work.getID());
            try {
                todo.put(work);
            } catch (InterruptedException e) {
                throw new RuntimeException("Something broke in producer", e);
            }
        }
    }

}
