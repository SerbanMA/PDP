package main.java.domain;

import java.util.Arrays;
import java.util.Random;

public class Matrix {

    private final Random random = new Random();

    private final int[][] matrix;
    private final int size;

    public Matrix(int size) {
        this.size = size;
        matrix = new int[size][size];
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public Matrix computeRandom() {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextInt(100);
            }
        }

        return this;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                string.append(String.format("%5d", matrix[i][j])).append(' ');
            }
            string.append('\n');
        }

        return string.toString();
    }
}
