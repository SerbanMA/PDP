package com.example.finalproject.domain;

import com.example.finalproject.threads.ThreadBlur;
import com.example.finalproject.threads.ThreadGray;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyImage {
    private int height;
    private int width;
    private String filePath;

    private int[][] r;
    private int[][] g;
    private int[][] b;
    private int[][] grayScale;
    private int[][][] blurMatrix;
    int blurSize;

    private int nrThreads = 10;

    public MyImage(String filePath, int blurSize) {
        this.filePath = filePath;
        this.blurSize = blurSize;
        prepareImageFromFile();
    }

    public MyImage(String filePath) {
        this.filePath = filePath;
        prepareImageFromFile();
    }

    private void prepareImageFromFile() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filePath));
            height = img.getHeight();
            width = img.getWidth();
            r = new int[height][width];
            g = new int[height][width];
            b = new int[height][width];
            grayScale = new int[height][width];
            blurMatrix = new int[height][width][3];

            int[] aux;
            for (int i = 0; i < height; i++)
                for (int j = 0; j < width; j++) {
                    aux = img.getRaster().getPixel(j, i, new int[3]);
                    r[i][j] = aux[0];
                    g[i][j] = aux[1];
                    b[i][j] = aux[2];
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[][][] createPixelMatrix(int[][] r, int[][] g, int[][] b) {
        int[][][] pixelMatrix = new int[height][width][3];

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                int[] pixel = new int[3];
                pixel[0] = r[i][j];
                pixel[1] = g[i][j];
                pixel[2] = b[i][j];
                pixelMatrix[i][j] = pixel;
            }

        return pixelMatrix;
    }


    private void exportImage(String fileName, int[][][] pixelMatrix) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                img.getRaster().setPixel(j, i, pixelMatrix[i][j]);
            }
        File outputfile = new File(fileName);
        try {
            ImageIO.write(img, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void grayImageFilter() {
        ExecutorService executorService = Executors.newFixedThreadPool(nrThreads);

        for (int i = 0; i < height; i++) {
            executorService.execute(new ThreadGray(i, width, r, g, b, grayScale));
        }

        executorService.shutdown();
        while(!executorService.isTerminated()) {}
    }

    public void exportGrayImageToFile(String fileName) {
        float start =  System.nanoTime() / 1000000;
        grayImageFilter();
        float end = System.nanoTime() / 1000000;
        exportImage(fileName, createPixelMatrix(grayScale, grayScale, grayScale));

        System.out.println(fileName + " time: " + (end - start) / 1000 + "s");
    }

    private void blurImageFilter() {
        ExecutorService executorService = Executors.newFixedThreadPool(nrThreads);

        for (int i = 0; i < height; i++) {
            executorService.execute(new ThreadBlur(i, height, width, blurSize, r, g, b, blurMatrix));
        }

        executorService.shutdown();
        while(!executorService.isTerminated()) {}
    }

    public void exportBlurImageToFile(String fileName) {
        float start =  System.nanoTime() / 1000000;
        blurImageFilter();
        float end = System.nanoTime() / 1000000;
        exportImage(fileName, blurMatrix);

        System.out.println(fileName + " time: " + (end - start) / 1000 + "ms");
    }
}