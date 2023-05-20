package com.my.parallel_computing.lab1;

import com.my.parallel_computing.lab5.server.Server;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Parallel {
    public static final Object mutex = new Object();
    public static Matrix doAction(Matrix matrix, int threadCount, String lab) throws InterruptedException {
        sleep(10000);
        int step = matrix.getDimension() / threadCount;
        int start, finish;
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            start = i * step;
            finish = start + step;
            if(i == threadCount-1)
                finish = matrix.getDimension();

            Runnable runnable = null;
            if(lab.equals(Main.LAB1))
                runnable = WorkerFactory.getDiagonalWorker(matrix, start, finish);
            if(lab.equals(Main.LAB3))
                runnable = WorkerFactory.getSumWorkerAtomic(matrix, start, finish);

            Thread thread = new Thread(runnable);

            threadList.add(thread);
            thread.start();
        }

        for(Thread thread : threadList){
            thread.join();
        }
        matrix.setFinished(true);
        return matrix;
    }
}

