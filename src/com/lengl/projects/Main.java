package com.lengl.projects;

public class Main {

    public static void main(String[] args) {
        Crystal crystal = new Crystal(Integer.parseInt(args[0]));
        int particlesNum = Integer.parseInt(args[1]);

        //рождаем pNum частиц и связываем их с семафором
        threadController semaphore = new threadController();
        for(int index = 0; index < particlesNum; index++){
            Thread j = new Particle(semaphore, crystal, Double.parseDouble(args[2]));
            crystal.addParticle();
            j.start();
        }

        int finalTime = Integer.parseInt(args[3]);
        for (int index = 0; index < finalTime; index++) {
            try {
                semaphore.renew(particlesNum);
            } catch (InterruptedException ex) {
                //do nothing?
            }
            //обработка - не надо ли напечатать масиив? На будущее
        }
        crystal.printCrystal();
        semaphore.finishJobs(particlesNum);
    }
}
