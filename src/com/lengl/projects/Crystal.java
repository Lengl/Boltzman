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
        this.distribution = new ArrayList<Integer>();
        for(int cellIndex = 0; cellIndex < cellsNum; cellIndex++ ){
            this.distribution.add(0);
        }
    }

    public void addParticle() {
        this.distribution.set(0, this.distribution.get(0) + 1);
        //this.particleNum++;
    }

    //Move particle from index to index+1 (if exists)
    public synchronized int moveRight(int index) {
        this.distribution.set(index, this.distribution.get(index) - 1);             //decrease Crystal[index]
        int endIndex = (index + 1) < this.distribution.size() ? index + 1 : index;  //calculate endIndex
        this.distribution.set(endIndex, this.distribution.get(endIndex) + 1);       //increase Crystal[endIndex]
        return endIndex;
    }

    //Move particle from index to index-1 (if exists)
    public synchronized int moveLeft(int index) {
        this.distribution.set(index, this.distribution.get(index) - 1);             //decrease Crystal[index]
        int endIndex = (index - 1) >= 0 ? index - 1 : index;                        //calculate endIndex
        this.distribution.set(endIndex, this.distribution.get(endIndex) + 1);       //increase Crystal[endIndex]
        return endIndex;
    }

    public synchronized void printCrystal () {
        for(int index = 0; index < this.distribution.size(); index++) {
            System.out.print(this.distribution.get(index) + " ");
        }
        System.out.println();
    }
}
