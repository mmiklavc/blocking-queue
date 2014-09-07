package com.michaelmiklavcic;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class FactoryTest {

    private static final long TIMEOUT = 3000;

    @Test
    public void single_producer_and_consumer_finish_all_work() {
        final int workTot = 1000;
        WorkManager man = new WorkManager(workTot);
        Factory factory = new Factory(man, 1, 1);
        factory.run();
        waitForWork(workTot, man);
        factory.stop();
        assertThat(man.getCount(), equalTo(workTot));
    }

    @Test
    public void multiple_producers_and_consumers_finish_all_work() {
        final int workTot = 100000;
        WorkManager man = new WorkManager(workTot);
        Factory factory = new Factory(man, 10, 10);
        factory.run();
        waitForWork(workTot, man);
        factory.stop();
        assertThat(man.getCount(), equalTo(workTot));
    }

    private void waitForWork(int work, WorkManager man) {
        long start = now();
        while (!timesUp(start, now()) && man.getCount() < work) {
            // wait
        }
    }

    private boolean timesUp(long start, long now) {
        return (now - start) > TIMEOUT;
    }

    private long now() {
        return System.currentTimeMillis();
    }

}
