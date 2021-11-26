package main.java.main;

import main.java.domain.Polynomial;
import main.java.service.Service;

public class Main {

    private static final Integer numberOfThreads = 6;

    public static void main(String[] args) throws InterruptedException {
        Polynomial polynomial1 = new Polynomial(7000);
        Polynomial polynomial2 = new Polynomial(7000);

        regularSequenceForm(polynomial1, polynomial2);
        regularParallelizedForm(polynomial1, polynomial2);
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
}
