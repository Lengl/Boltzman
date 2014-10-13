package com.lengl.projects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Lengl on 12.10.2014.
 */
public class Crystal {
    private List<Integer> distribution;
    //private int particleNum

    Crystal(int cellsNum) {
        //this.particleNum = particleNum
        this.distribution = new ArrayList<Integer>();
        for(int index = 0; index < cellsNum; index++ ){
            this.distribution.add(0);
        }
    }

    public void addParticle() {
        this.distribution.set(0, this.distribution.get(0) + 1);
    }

    //Двигаем частицу из ячейки index в конец кристалла
    public synchronized int moveRight(int index) {
        this.distribution.set(index, this.distribution.get(index) - 1);
        int endIndex = (index + 1) < this.distribution.size() ? index + 1 : index;
        this.distribution.set(endIndex, this.distribution.get(endIndex) + 1);
        return endIndex;
    }

    //Двигаем частицу из ячейки index в начало кристалла
    public synchronized int moveLeft(int index) {
        this.distribution.set(index, this.distribution.get(index) - 1);
        int endIndex = (index - 1) >= 0 ? index - 1 : index;
        this.distribution.set(endIndex, this.distribution.get(endIndex) + 1);
        return endIndex;
    }

    public synchronized void printCrystal () {
        for(int index = 0; index < this.distribution.size(); index++) {
            System.out.print(this.distribution.get(index) + " ");
        }
        System.out.println();
    }
}
