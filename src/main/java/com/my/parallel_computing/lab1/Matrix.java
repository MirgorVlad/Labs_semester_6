package com.my.parallel_computing.lab1;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class Matrix implements Serializable {
    private int dimension;
    private int[][] matrix;

    public Matrix(int dimension){
        this.dimension = dimension;
        matrix = new int[dimension][dimension];
    }


    public void fillMatrixByRandomValues(){
        final int maxValue = 1000;
        Random random = new Random();

        for(int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++) {
                matrix[i][j] = random.nextInt(maxValue);
            }
        }
    }

    public void fillMatrixByValue(int val){

        for(int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++) {
                matrix[i][j] = val;
            }
        }
    }

    public void printMatrix() {

        for(int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++) {
                System.out.printf("%-4d ",matrix[i][j]);
            }
            System.out.println();
        }
    }

    public void swapElements(int a1, int a2, int b1, int b2){
        int aCopy = matrix[a1][a2];
        matrix[a1][a2] = matrix[b1][b2];
        matrix[b1][b2] = aCopy;
    }

    public int findMinElementIndexAtColumn(int column){
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < dimension; i++) {
            if(matrix[i][column] < min) {
                min = matrix[i][column];
                minIndex = i;
            }
        }
        return minIndex;
    }


    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
}
