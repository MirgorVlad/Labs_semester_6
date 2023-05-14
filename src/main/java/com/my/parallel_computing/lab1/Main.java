package com.my.parallel_computing.lab1;

import org.apache.commons.lang3.SerializationUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    public static final String LAB1 = "lab1";
    public static final String LAB3 = "lab3";

    public static Long sum = 0L;
    public static AtomicLong sumAtomic = new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {

        int[] arr = new int[]{1,2,3,1};


        Matrix matrix = new Matrix(20000);
        matrix.fillMatrixByRandomValues();


        long start = System.nanoTime();
        Parallel.doAction(matrix,2, LAB3);
        long finish = System.nanoTime();

        System.out.println("Sum = " + sum);
        //System.out.println("Atomic sum = " + sumAtomic);
        System.out.println("Operation time: " + (finish - start)/1000000 + "ms");

    }
}
