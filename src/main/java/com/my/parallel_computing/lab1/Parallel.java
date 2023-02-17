package com.my.parallel_computing.lab1;

import java.util.ArrayList;
import java.util.List;

public class Parallel {
    public static void doAction(Matrix matrix, int threadCount) throws InterruptedException {
        int step = matrix.getDimension() / threadCount;
        int start, finish;
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            start = i * step;
            finish = start + step;
            if(i == threadCount-1)
                finish = matrix.getDimension();

            Runnable runnable1 = new CountingThread(matrix, start, finish);
            Thread thread = new Thread(runnable1);
            threadList.add(thread);
            thread.start();
        }

        for(Thread thread : threadList){
            thread.join();
        }

    }
}

class CountingThread implements Runnable{

    public int start;
    public int end;
    public Matrix matrix;

    public CountingThread(Matrix matrix, int start, int end){
        this.matrix = matrix;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        int minElementIndex;
        int dimension = matrix.getDimension();
        for (int i = start; i < end; i++) {
            minElementIndex = matrix.findMinElementIndexAtColumn(i);
            matrix.swapElements(minElementIndex, i, dimension-1-i, i);
        }
    }
}