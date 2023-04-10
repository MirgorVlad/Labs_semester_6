package com.my.parallel_computing.lab1;

import com.my.parallel_computing.lab1.worker.DiagonalWorker;
import com.my.parallel_computing.lab1.worker.SumWorker;
import com.my.parallel_computing.lab1.worker.SumWorkerAtomic;

public class WorkerFactory {
    public static Runnable getDiagonalWorker(Matrix matrix, int start, int end){
        return new DiagonalWorker(matrix, start, end);
    }

    public static Runnable getSumWorker(Matrix matrix, int start, int end){
        return new SumWorker(matrix, start, end);
    }

    public static Runnable getSumWorkerAtomic(Matrix matrix, int start, int end){
        return new SumWorkerAtomic(matrix, start, end);
    }
}
