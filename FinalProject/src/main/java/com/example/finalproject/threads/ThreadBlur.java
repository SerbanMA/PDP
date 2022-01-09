package com.example.finalproject.threads;

public class ThreadBlur implements Runnable {
    private final int line;
    private final int height;
    private final int width;
    private final int size;

    private final int[][] r;
    private final int[][] g;
    private final int[][] b;
    private final int[][][] blurMatrix;

    public ThreadBlur(int line, int height, int width, int size, int[][] r, int[][] g, int[][] b, int[][][] blurMatrix) {
        this.line = line;
        this.height = height;
        this.width = width;
        this.size = size;
        this.r = r;
        this.g = g;
        this.b = b;
        this.blurMatrix = blurMatrix;
    }

    public synchronized void run() {
        for (int column = 0; column < width; column++)
            blurMatrix[line][column] = getLuminancePixel(line, column, size);
    }

    private synchronized int[] getLuminancePixel(int x, int y, int size) {
        int[] average = new int[3];
        int count = 0;
        for (int i = x - size; i <= x + size; i++) {
            if (i >= 0 && i < height) {
                for (int j = y - size; j <= y + size; j++) {
                    if (j >= 0 && j < width) {
                        if (x != i || y != j) {
                            count++;
                            average[0] += r[i][j];
                            average[1] += g[i][j];
                            average[2] += b[i][j];
                        }
                    }
                }
            }
        }
        if (count != 0) {
            average[0] = average[0] / count;
            average[1] = average[1] / count;
            average[2] = average[2] / count;
        }

        return average;
    }
}
