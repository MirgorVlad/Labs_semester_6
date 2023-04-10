package com.my.parallel_computing.lab1.worker;

import com.my.parallel_computing.lab1.Main;
import com.my.parallel_computing.lab1.Matrix;
import com.my.parallel_computing.lab1.Parallel;

public class SumWorker implements Runnable{
    public int start;
    public int end;
    public Matrix matrix;

    public SumWorker(Matrix matrix, int start, int end){
        this.matrix = matrix;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = start; i < end; i++) {
            int[] row = matrix.getMatrix()[i];
            for(int n : row){
                if(n % 15 == 0) {
                    sum += n;
                }
            }
        }
        long start = System.nanoTime();
        synchronized (Parallel.mutex) {
            Main.sum += sum;
        }
        long finish = System.nanoTime();
        System.out.println("Block time: " + (finish - start) / 1000000 + "ms");
    }
}

