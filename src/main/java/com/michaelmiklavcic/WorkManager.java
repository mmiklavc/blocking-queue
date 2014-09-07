package com.michaelmiklavcic;

import java.util.Random;

public class WorkManager {
    private Random idgen;
    private int workToWhip;
    private volatile boolean open;
    private volatile int created;
    private volatile int done;

    public WorkManager(int workToWhip) {
        this.workToWhip = workToWhip;
        idgen = new Random();
        open = true;
    }

    public int getCount() {
        return done;
    }

    public boolean saysProduce() {
        synchronized (this) {
            return created < workToWhip;
        }
    }

    public Work registerAssignment(Work work) {
        synchronized (this) {
            created++;
        }
        return work.setID("id" + idgen.nextInt(1000));
    }

    public boolean saysConsume() {
        return open;
    }

    public void report() {
        synchronized (this) {
            done++;
        }
    }

    public void closeShop() {
        open = false;
    }

}
