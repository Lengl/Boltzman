package com.lengl.projects;

public class Main {

    //arguments should be: cellsNum particlesNum probability finalTime
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Error: Wrong number of argument");
            System.exit(0);
        }

        //this try-catch block looks like a huge crutch: I'm sure it could be better but I have no idea how.
        try {
            int cellsNum = Integer.parseInt(args[0]);
            if (cellsNum <= 0) {
                System.out.println("Error: cellsNum must be positive");
                System.exit(0);
            }
            Crystal crystal = new Crystal(cellsNum);
            int particlesNum = Integer.parseInt(args[1]);
            if (particlesNum <= 0) {
                System.out.println("Error: particlesNum must be positive");
                System.exit(0);
            }
            double probability = Double.parseDouble(args[2]);
            if (!(probability >= 0 && probability <= 1.0)) {
                System.out.println("Error: Probability should be in [0, 1]");
                System.exit(0);
            }
            int finalTime = Integer.parseInt(args[3]);
            if(finalTime <= 0) {
                System.out.println("Error: finalTime must be positive");
            }

            //create pNum particles and link them to semaphore
            threadController semaphore = new threadController();
            for (int index = 0; index < particlesNum; index++) {
                Thread particleThread = new Particle(semaphore, crystal, probability);
                crystal.addParticle();
                particleThread.start();
            }

            //do finalTime ticks of clock
            for (int timeCount = 0; timeCount < finalTime; timeCount++) {
                try {
                    semaphore.renew(particlesNum);
                } catch (InterruptedException ex) {
                    //do nothing?
                }
            }

            crystal.printCrystal();
            semaphore.finishJobs(particlesNum);
        } catch (NumberFormatException ex) {
            System.out.println("Error: wrong arguments: " + ex.getMessage());
        }
    }
}
