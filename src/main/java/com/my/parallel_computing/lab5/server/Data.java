package com.my.parallel_computing.lab5.server;

import com.my.parallel_computing.lab1.Matrix;

public class Data {
    private Matrix matrix;
    private int threadCount;

    public Data(Matrix matrix, int threadCount) {
        this.matrix = matrix;
        this.threadCount = threadCount;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }
}
