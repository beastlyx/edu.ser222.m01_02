package edu.ser222.m01_02;

/**
 * An implementation of the Matrix ADT. Provides four basic operations over an immutable type.
 * 
 * Last updated 01/10/2024.
 * 
 * @author Borys Banaszkiewicz, Ruben Acuna
 * @version 1.0
 */

public class CompletedMatrix implements Matrix {
    
    private int[][] mat;
    private int n;
    private int m;
    
    public CompletedMatrix(int[][] matrix) {        
        if (matrix == null) throw new java.lang.IllegalArgumentException("Null input");
                
        if (matrix.length == 0) {
            this.mat = new int[0][0];
            return;
        }
        
        this.n = matrix.length;
        this.m = matrix[0].length;
        
        this.mat = new int[this.n][this.m];
        
        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.m; col++) {
                this.mat[row][col] = matrix[row][col];
            }
        }
    }

    @Override
    public int getElement(int y, int x) {        
        return this.mat[y][x];
    }

    @Override
    public int getRows() {
        if (this.n == 0) return 0;
        return this.n;
    }

    @Override
    public int getColumns() {           
        if (this.n == 0) return 0;
        return this.m;
    }

    @Override
    public Matrix scale(int scalar) {                
        int[][] temp = new int[this.n][this.m];
        
        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.m; col++) {
                temp[row][col] = this.mat[row][col] * scalar;
            }
        }
        
        return new CompletedMatrix(temp);
    }

    @Override
    public Matrix plus(Matrix other) {
        if (other == null) throw new IllegalArgumentException("matrix is null");
        if (this.getRows() != other.getRows() || this.getColumns() != other.getColumns()) throw new RuntimeException("matrices do not have matching dimensions");
        
        int[][] temp = new int[this.n][this.m];
        
        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.m; col++) {
                temp[row][col] = this.mat[row][col] + other.getElement(row, col);
            }
        }
        
        return new CompletedMatrix(temp);
    }

    @Override
    public Matrix minus(Matrix other) {
        if (other == null) throw new IllegalArgumentException("matrix is null");
        if (this.getRows() != other.getRows() || this.getColumns() != other.getColumns()) throw new RuntimeException("matrices do not have matching dimensions");
        
        int[][] temp = new int[this.n][this.m];
        
        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.m; col++) {
                temp[row][col] = this.mat[row][col] - other.getElement(row, col);
            }
        }
        
        return new CompletedMatrix(temp);
    }

    @Override
    public Matrix multiply(Matrix other) {
        if (other == null) throw new IllegalArgumentException("matrix is null");
        if (this.getColumns() != other.getRows()) throw new RuntimeException("matrices do not have matching dimensions");
        
        int m_other = other.getColumns();
        int r = other.getRows();
        
        int[][] temp = new int[this.n][m_other];
        
        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < m_other; col++) {
                int prod = 0;
                for (int i = 0; i < r; i++) {
                    prod += this.mat[row][i] * other.getElement(i,col);
                }
                temp[row][col] = prod;
            }
        }
        
        return new CompletedMatrix(temp);
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null || other.getClass() != this.getClass()) return false;

        CompletedMatrix temp = (CompletedMatrix) other;
        
        if (this.n != temp.getRows() || this.m != temp.getColumns()) return false;

        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.m; col++) {
                if (this.mat[row][col] != temp.getElement(row, col)) return false;
            }
        }
    
        return true;
    }
    
    @Override
    public String toString() {
        
        StringBuilder str = new StringBuilder();
        
        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.m; col++) {
                str.append(this.mat[row][col]).append(" ");
            }
            str.append("\n");
        }
        
        return str.toString();
    }

    /**
     * Entry point for matrix testing.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //These tests show sample usage of the matrix, and some basic ideas for testing. They are not comprehensive.

        int[][] data1 = new int[0][0];
        int[][] data2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] data3 = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
        int[][] data4 = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
        int[][] data5 = {{1, 4, 7}, {2, 5, 8}};

        Matrix m1 = new CompletedMatrix(data1);
        Matrix m2 = new CompletedMatrix(data2);
        Matrix m3 = new CompletedMatrix(data3);
        Matrix m4 = new CompletedMatrix(data4);
        Matrix m5 = new CompletedMatrix(data5);

        System.out.println("m1 --> Rows: " + m1.getRows() + " Columns: " + m1.getColumns());
        System.out.println("m2 --> Rows: " + m2.getRows() + " Columns: " + m2.getColumns());
        System.out.println("m3 --> Rows: " + m3.getRows() + " Columns: " + m3.getColumns());

        //check for reference issues
        System.out.println("m2 -->\n" + m2);
        data2[1][1] = 101;
        System.out.println("m2 -->\n" + m2);

        //test equals
        System.out.println("m2==null: " + m2.equals(null));             //false
        System.out.println("m3==\"MATRIX\": " + m2.equals("MATRIX"));   //false
        System.out.println("m2==m1: " + m2.equals(m1));                 //false
        System.out.println("m2==m2: " + m2.equals(m2));                 //true
        System.out.println("m2==m3: " + m2.equals(m3));                 //false
        System.out.println("m3==m4: " + m3.equals(m4));                 //true

        //test operations (valid)
        System.out.println("m1 + m1:\n" + m1.plus(m1));
        System.out.println("m1 + m1:\n" + m1.plus(m1));
        System.out.println("2 * m2:\n" + m2.scale(2));
        System.out.println("m2 + m3:\n" + m2.plus(m3));
        System.out.println("m2 - m3:\n" + m2.minus(m3));
        System.out.println("3 * m5:\n" + m5.scale(3));

        //not tested... multiply(). you know what to do.
//        System.out.println("m1 * m1:\n" + m1.multiply(m1));
//        System.out.println("m1 * m2:\n" + m1.multiply(m2));
//        System.out.println("m2 * m3:\n" + m2.multiply(m3));
//        System.out.println("m4 * m3:\n" + m4.multiply(m3));
//        System.out.println("m3 * m4:\n" + m3.multiply(m4));
//        System.out.println("m3 * m5:\n" + m3.multiply(m5));
        //test operations (invalid)
//        System.out.println("m1 + m2" + m1.plus(m2));
//        System.out.println("m1 + m5" + m1.plus(m5));
//        System.out.println("m1 - m2" + m1.minus(m2));
    }
}