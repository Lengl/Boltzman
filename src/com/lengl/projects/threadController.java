package com.lengl.projects;

/**
 * Created by Lengl on 12.10.2014.
 * basically, partly copypasted from http://www.javamex.com/tutorials/synchronization_wait_notify_4.shtml
 */
public class threadController {

    private final Object childSync = new Object();
    private int count = 0;                  //count for unmoved paricles
    private int lifetime = 0;               //global clock
    private boolean finisher = false;       //threadKiller

    public void childAwaitForJob(int lifetime) throws InterruptedException {
        synchronized (childSync) {
            while (this.lifetime != lifetime) {
                childSync.wait();
            }
            if(finisher) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void countDown() {
        synchronized (childSync) {
            if (--count <= 0) {
                //wake parent
                count = 0;
                childSync.notifyAll();
            }
        }
    }

    public void renew(int newParticlesNum) throws InterruptedException{
        synchronized (childSync) {
            this.lifetime++;
            this.count = newParticlesNum;
            //wake childThreads
            childSync.notifyAll();
            while (count > 0) {
                childSync.wait();
            }
        }
    }

    public void finishJobs(int newParticlesNum) {
        synchronized (childSync) {
            this.lifetime++;
            this.count = newParticlesNum;
            finisher = true;
            //wake childThreads for exit
            childSync.notifyAll();
        }
    }
}