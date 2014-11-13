package com.lengl.projects;

import java.util.Random;

/**
 * Created by Lengl on 12.10.2014.
 */
public class Particle extends Thread {
    private threadController semaphore;
    private int position = 0;                   //position in Crystal
    private int lifetime = 1;                   //particle's clock
    private double probabilityRight;            //probability to go to the array's end
    private Crystal home;                       //base Crystal for this particle

    Particle(threadController semaphore, Crystal crystal, double probabilityRight) {
        this.home = crystal;
        this.probabilityRight = probabilityRight;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        while(true) {
            //waiting for the watch to match
            try {
                semaphore.childAwaitForJob(lifetime);
            } catch(InterruptedException ex) {
                break;
                //do nothing?
            }
            try {
                //decide where to move
                Random generator = new Random();
                double result = generator.nextDouble();
                if (Double.compare(result, probabilityRight) < 0) {
                    position = home.moveRight(position);
                } else {
                    position = home.moveLeft(position);
                }
                //sleep
                try {
                    Thread.sleep(1000);                 //1000 milliseconds is one second.
                } catch (InterruptedException ex) {
                    break;
                    //do nothing?
                }
            } finally {
                //tell to the parent we finished
                this.lifetime++;
                semaphore.countDown();
            }
        }
    }
}
