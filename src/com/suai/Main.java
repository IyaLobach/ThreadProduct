package com.suai;

import com.suai.matrix.UsualMatrix;

public class Main {

    public static void main(String[] args) {
        try {
            UsualMatrix m1 = new UsualMatrix(1000,1000);
            UsualMatrix m2 = new UsualMatrix(1000,1000);
            long start1 = System.currentTimeMillis();
            System.out.println(m1.usualProduct(m2));
            long finish1 = System.currentTimeMillis();
            System.out.println("UsualMatrixProduct: " + (finish1 - start1) + " ms");
            long start2 = System.currentTimeMillis();
            System.out.println(m1.parallelProduct(m2,2));
            long finish2 = System.currentTimeMillis();
            System.out.println("ParallelMatrixProduct: " + (finish2 - start2) + " ms");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
