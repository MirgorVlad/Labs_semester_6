package com.my.parallel_computing.lab1.worker;

import com.my.parallel_computing.lab1.Matrix;

public class DiagonalWorker implements Runnable{

    public int start;
    public int end;
    public Matrix matrix;

    public DiagonalWorker(Matrix matrix, int start, int end){
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
            synchronized (this) {
                matrix.swapElements(minElementIndex, i, dimension - 1 - i, i);
            }
        }
    }
}