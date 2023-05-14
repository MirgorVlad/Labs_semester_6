package com.my.parallel_computing;

import mpi.*;
public class MPJTest {
    public static void main(String args[]) throws Exception {
        MPI.Init(args);
        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        System.out.println("Hello world from <"+me+"> from <"+size+">");
        MPI.Finalize();
    }
}
