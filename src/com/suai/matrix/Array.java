package com.suai.matrix;

import java.util.ArrayList;

public class Array {
    class Worker extends Thread {
        private int localMin;
        private int start;
        private int finish;

        public Worker(int indexStart, int indexFinish){
            localMin = Integer.MAX_VALUE;
            start = indexStart;
            finish = indexFinish;
        }
        public void run(){
            for (int i = start; i < finish; i++){
                if (al.get(i) < localMin)
                    localMin = al.get(i);
            }
        }
        public int getResult() { return localMin; }
    }

    private ArrayList<Integer> al;

    public Array(int size){
        int max = Integer.MAX_VALUE;
        int min = Integer.MIN_VALUE;
        al = new ArrayList<>();
        for (int i = 0; i < size; i++)
            al.add((int)(Math.random()*(max - min) + min));
    }
    public int findMin(){
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < al.size(); i++){
            if (al.get(i) < min)
                min = al.get(i);
        }
        return min;
    }
    public String toString(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < al.size(); i++){
            str.append(al.get(i) + " ");
        }
        str.append("\n");
        return str.toString();
    }
    public int threadFindMin(int threadNum) throws Exception{
        Worker[] workers = new Worker[threadNum];
        int parts = al.size() / threadNum;
        for (int i = 0; i < threadNum; i++){
            if (i == (threadNum - 1))
                workers[i] = new Worker(i*parts, al.size());
            else
                workers[i] = new Worker(i*parts, i*parts + parts);
        }

        long start2 = System.currentTimeMillis();
        for (int i = 0; i < threadNum; i++){
            workers[i].start();
        }

        for (int i = 0; i < threadNum; i++)
            workers[i].join();


        int globalMin = Integer.MAX_VALUE;
        for (int i = 0; i < threadNum; i++){
            if (workers[i].getResult() < globalMin)
                globalMin = workers[i].getResult();
        }
        long finish2 = System.currentTimeMillis();
        System.out.println("Thread " + (finish2 - start2) + " ms");
        return globalMin;
    }
    public static void main(String[] args){
        try {
            Array l = new Array(10000000);
            long start1 = System.currentTimeMillis();
            System.out.println(l.findMin());
            long finish1 = System.currentTimeMillis();
            System.out.println("Usual: " + (finish1 - start1) + " ms");
            System.out.println(l.threadFindMin(1000));

        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
