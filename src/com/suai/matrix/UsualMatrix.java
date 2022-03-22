package com.suai.matrix;

public class UsualMatrix {

   protected int[][] matrix;

    public UsualMatrix(int row, int col){
        if (row > 0 && col > 0) {
            matrix = new int[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++)
                    matrix[i][j] = i;
            }
        }
        else
            throw new RuntimeException("Incorrect size");

    }
    public UsualMatrix(int[][] m){
        matrix = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++)
                matrix[i][j] = m[i][j];
        }
    }
    public UsualMatrix parallelProduct(UsualMatrix another, int threadSize) throws Exception {
        ParallelMatrixProduct product = new ParallelMatrixProduct(threadSize, this, another);
        return new UsualMatrix(product.getResult());
    }
    public UsualMatrix usualProduct(UsualMatrix another){
        if (another == null || matrix[0].length != another.matrix.length)
            throw new RuntimeException("Multiplication is impossible. Different matrix sizes");
        int[][] res = new int[matrix.length][another.matrix[0].length];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < another.matrix[0].length; j++){
                for (int k = 0; k < another.matrix.length; k++)
                    res[i][j] += matrix[i][k] * another.matrix[k][j];
            }
        }
        return new UsualMatrix(res);
    }
    public String toString(){
        StringBuilder str = new StringBuilder();
        /*for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++)
                str.append(matrix[i][j] + " ");
            str.append("\n");
       }*/
        //str.append(matrix[matrix.length - 1][matrix[0].length - 1]);
        return str.toString();
    }
}
