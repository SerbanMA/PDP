package main.java.repository;

import main.java.domain.Element;
import main.java.domain.Matrix;

public class Repository {

    private final Matrix matrixA;
    private final Matrix matrixB;
    private final Matrix matrixC;

    private final int matrixSize;

    public Repository(Matrix matrixA, Matrix matrixB, Matrix matrixC, int matrixSize) {

        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.matrixC = matrixC;

        this.matrixSize = matrixSize;
    }

    public int getMatrixSize() {
        return matrixSize;
    }

    public Matrix getMatrixA() {
        return matrixA;
    }

    public Matrix getMatrixB() {
        return matrixB;
    }

    public Matrix getMatrixC() {
        return matrixC;
    }
}
