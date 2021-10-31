package main.java.helper;

import main.java.domain.Element;

public class MatrixHelper {

    public static int getPosition(int size, int line, int column) {
        return line * size + column + 1;
    }

    public static Element getElementByPosition(int size, int position) {
        int line = (position - 1) / size;
        int column = (position - 1) % size;

        return new Element(line, column);
    }

    public static Element getOppositeElementByPosition(int size, int position) {
        int column = (position - 1) / size;
        int line = (position - 1) % size;

        return new Element(line, column);
    }

    public static int computeProduct(int[][] matrixA, int[][] matrixB, int size, int line, int column) {

        int solution = 0;

        for (int i = 0; i < size; i++) {
            solution += matrixA[line][i] * matrixB[i][column];
        }

        return solution;
    }
}
