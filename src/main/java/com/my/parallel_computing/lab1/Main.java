package com.my.parallel_computing.lab1;

import org.apache.commons.lang3.SerializationUtils;

import java.util.Objects;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        //System.out.println("Initial matrix:");
        Matrix matrix = new Matrix(20000);
        matrix.fillMatrixByRandomValues();
       // matrix.printMatrix();

        //System.out.println("\nSorted matrix (sequential):");
        long start = System.nanoTime();
        Parallel.doAction(matrix,2);
        long finish = System.nanoTime();
        //matrix.printMatrix();

        System.out.println("Operation time: " + (finish - start)/1000000 + "ms");

    }
}
