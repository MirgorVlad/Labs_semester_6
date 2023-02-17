package com.my.parallel_computing.lab1;

public class Sequential {
    public static void doAction(Matrix matrix){
        int minElementIndex;
        int dimension = matrix.getDimension();
        for (int i = 0; i < dimension; i++) {
            minElementIndex = matrix.findMinElementIndexAtColumn(i);
            matrix.swapElements(minElementIndex, i, dimension-1-i, i);
        }
    }
}
