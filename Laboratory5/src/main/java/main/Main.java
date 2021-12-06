package main.java.main;

import main.java.domain.Polynomial;
import main.java.service.Service;

import java.util.concurrent.ExecutionException;

public class Main {

    private static final int numberOfThreads = 6;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Polynomial polynomial1 = new Polynomial(10000);
        Polynomial polynomial2 = new Polynomial(10000);

        regularSequenceForm(polynomial1, polynomial2);
        regularParallelizedForm(polynomial1, polynomial2);
        karatsubaSequenceForm(polynomial1, polynomial2);
        karatsubaParallelizedForm(polynomial1, polynomial2);
    }

    private static void regularSequenceForm(Polynomial polynomial1, Polynomial polynomial2) {
        long startTime = System.currentTimeMillis();
        Service.regularSequenceForm(polynomial1, polynomial2);
        long endTime = System.currentTimeMillis();
        System.out.println("Simple sequence multiplication of polynomials execution time: " + (endTime - startTime) + "ms");
    }

    private static void regularParallelizedForm(Polynomial polynomial1, Polynomial polynomial2) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Service.regularParallelizedForm(polynomial1, polynomial2, numberOfThreads);
        long endTime = System.currentTimeMillis();
        System.out.println("Simple parallelized multiplication of polynomials execution time: " + (endTime - startTime) + "ms");
    }

    private static void karatsubaSequenceForm(Polynomial polynomial1, Polynomial polynomial2) {
        long startTime = System.currentTimeMillis();
        Service.karatsubaSequenceForm(polynomial1, polynomial2);
        long endTime = System.currentTimeMillis();
        System.out.println("Karatsuba sequence multiplication of polynomials execution time: " + (endTime - startTime) + "ms");
    }

    private static void karatsubaParallelizedForm(Polynomial polynomial1, Polynomial polynomial2) throws InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();
        Service.karatsubaParallelizedForm(polynomial1, polynomial2, 0);
        long endTime = System.currentTimeMillis();
        System.out.println("Karatsuba parallelized multiplication of polynomials execution time: " + (endTime - startTime) + "ms");
    }
}
