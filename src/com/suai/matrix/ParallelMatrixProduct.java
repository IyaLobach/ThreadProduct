package com.suai.matrix;


public class ParallelMatrixProduct {

    // рабочий поток
    class Worker extends Thread {

        private int[] res;
        private UsualMatrix m2;
        private int[] matrix1Row;

        public Worker()
        {
            m2 = null;
            matrix1Row = null;
            res = null;
        }

        public Worker(UsualMatrix another, int[] m1)
        {
            m2 = another;
            matrix1Row = m1;
            res = new int[m2.matrix[0].length];
        }

        public void set(UsualMatrix another, int[] m1)
        {
            m2 = another;
            matrix1Row = m1;
            res = new int[m2.matrix[0].length];
        }

        public void run() {
            for(int j = 0; j < res.length; j++){
               for (int k = 0; k < m2.matrix.length ; k++)
                    res[j] += matrix1Row[k] * m2.matrix[k][j];
            }
        }

        public int[] getResult() { return res; }
    }


    private int[][] res;

    //коориднатор
    public ParallelMatrixProduct(int threadSize, UsualMatrix m1, UsualMatrix m2) throws Exception {
        if (m1 == null || m2 == null || m1.matrix[0].length != m2.matrix.length)
            throw new RuntimeException("Multiplication is impossible. Different matrix sizes");
        if (threadSize <= 0 || threadSize > m1.matrix.length)
            throw new RuntimeException("Incorrect threadSize");

        res = new int[m1.matrix.length][m2.matrix[0].length];

        Worker[] workers = new Worker[threadSize];
        for (int i = 0; i < threadSize; i++) {
            workers[i] = new Worker();
        }


        for (int i = 0; i < m1.matrix.length; i++){
            int j = i % threadSize;
            while(workers[j].getState() == Thread.State.RUNNABLE)
                j = (++j) % threadSize;
            workers[j] = new Worker(m2, m1.matrix[i]);
            workers[j].start();
            res[i] = workers[j].getResult();

        }

        for (int i = 0; i < threadSize; i++) {
           workers[i].join();
        }
    }

    public int[][] getResult() { return res; }
}
