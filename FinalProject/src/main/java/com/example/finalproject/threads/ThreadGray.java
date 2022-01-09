package com.example.finalproject.threads;

public class ThreadGray implements Runnable {
    private final static double redCoefficient = 0.299;
    private final static double greenCoefficient = 0.587;
    private final static double blueCoefficient = 0.114;
    private final static int black = 255;

    private final int width;
    private final int line;

    private final int[][] r;
    private final int[][] g;
    private final int[][] b;
    private final int[][] grayScale;

    public ThreadGray(int line, int width, int[][] r, int[][] g, int[][] b, int[][] grayScale) {
        this.line = line;
        this.width = width;
        this.r = r;
        this.g = g;
        this.b = b;
        this.grayScale = grayScale;

    }

    @Override
    public void run() {
        for (int column = 0; column < width; column++) {
            int sum = (int) (r[line][column] * redCoefficient + g[line][column] * greenCoefficient + b[line][column] * blueCoefficient);

            grayScale[line][column] = Math.min(sum, black);
        }
    }
}