package com.lengl.projects;

import java.util.Random;

/**
 * Created by Lengl on 12.10.2014.
 */
public class Particle extends Thread {
    private threadController semaphore;
    private int position = 0; //позиция частицы в кристалле
    private int lifetime = 1; //секундомер частицы
    private double probabilityRight; //вероятность для частицы сходить к концу массива
    private Crystal home;   //родной кристалл

    Particle(threadController semaphore, Crystal crystal, double probabilityRight) {
        this.home = crystal;
        this.probabilityRight = probabilityRight;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        while(true) {
            //ждём совпадения секундомеров
            try {
                semaphore.childAwaitForJob(lifetime);
            } catch(InterruptedException ex) {
                break;
                //do nothing?
            }
            try {
                //Определяем, куда двигаться
                Random generator = new Random();
                double result = generator.nextDouble();
                if (Double.compare(result, probabilityRight) < 0) {
                    position = home.moveRight(position);
                } else {
                    position = home.moveLeft(position);
                }
                //спим
                try {
                    Thread.sleep(1000);                 //1000 milliseconds is one second.
                } catch (InterruptedException ex) {
                    break;
                    //do nothing?
                }
            } finally {
                //сообщаем, что работа завершена и переводим часы.
                this.lifetime++;
                semaphore.countDown();
            }
        }
    }
}
