package main.java.service;

import main.java.domain.Polynomial;
import main.java.helper.PolynomialHelper;
import main.java.helper.RegularTask;

import java.util.concurrent.*;

public class Service {

    public static Polynomial regularSequenceForm(Polynomial polynomial1, Polynomial polynomial2) {
        Integer polynomialResultDegree = polynomial1.getDegree() + polynomial2.getDegree();
        Polynomial polynomialResult = new Polynomial(polynomialResultDegree).clear();

        for (int i = 0; i < polynomial1.getSize(); i++)
            for (int j = 0; j < polynomial2.getSize(); j++) {
                int index = i + j;
                Integer value = polynomial1.getCoefficient(i) * polynomial2.getCoefficient(j);

                polynomialResult.setCoefficient(index, polynomialResult.getCoefficient(index) + value);
            }

        return polynomialResult;
    }

    public static Polynomial regularParallelizedForm(Polynomial polynomial1, Polynomial polynomial2, Integer numberOfThreads) throws InterruptedException {
        Integer polynomialResultDegree = polynomial1.getDegree() + polynomial2.getDegree();
        Polynomial polynomialResult = new Polynomial(polynomialResultDegree).clear();

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfThreads);

        int step = polynomialResult.getSize() / numberOfThreads;
        if (step == 0) step = 1;

        for (int start = 0; start < polynomialResult.getSize(); start+=step) {
            RegularTask task = new RegularTask(start, start + step, polynomial1, polynomial2, polynomialResult);
            executor.execute(task);
        }
        executor.shutdown();
        executor.awaitTermination(50, TimeUnit.SECONDS);

        return  polynomialResult;
    }

    public static Polynomial karatsubaSequenceForm(Polynomial polynomial1, Polynomial polynomial2) {
        if (polynomial1.getSize() <= 1 || polynomial2.getSize() <= 1) {
            return regularSequenceForm(polynomial1, polynomial2);
        }

        int halfLength = Math.min(polynomial1.getSize(), polynomial2.getSize()) / 2;
        Polynomial lowPolynomial1 = new Polynomial(polynomial1.getCoefficients().subList(0, halfLength));
        Polynomial highPolynomial1 = new Polynomial(polynomial1.getCoefficients().subList(halfLength, polynomial1.getSize()));
        Polynomial lowPolynomial2 = new Polynomial(polynomial2.getCoefficients().subList(0, halfLength));
        Polynomial highPolynomial2 = new Polynomial(polynomial2.getCoefficients().subList(halfLength, polynomial2.getSize()));

        Polynomial karatsuba1 = karatsubaSequenceForm(lowPolynomial1, lowPolynomial2);
        Polynomial karatsuba2 = karatsubaSequenceForm(PolynomialHelper.add(lowPolynomial1, highPolynomial1), PolynomialHelper.add(lowPolynomial2, highPolynomial2));
        Polynomial karatsuba3 = karatsubaSequenceForm(highPolynomial1, highPolynomial2);

        Polynomial polynomialResult1 = PolynomialHelper.shift(karatsuba3, 2 * halfLength);
        Polynomial polynomialResult2 = PolynomialHelper.shift(PolynomialHelper.subtract(PolynomialHelper.subtract(karatsuba2, karatsuba3), karatsuba1) , halfLength);

        return PolynomialHelper.add(PolynomialHelper.add(polynomialResult1, polynomialResult2), karatsuba3);
    }

    public static Polynomial karatsubaParallelizedForm(Polynomial polynomial1, Polynomial polynomial2, Integer currentDepth) throws InterruptedException, ExecutionException {
        if (currentDepth > 4) {
            return  karatsubaSequenceForm(polynomial1, polynomial2);
        }

        if (polynomial1.getSize() <= 1 || polynomial2.getSize() <= 1) {
            return regularSequenceForm(polynomial1, polynomial2);
        }

        int newDepth = currentDepth + 1;
        int halfLength = Math.min(polynomial1.getSize(), polynomial2.getSize()) / 2;
        Polynomial lowPolynomial1 = new Polynomial(polynomial1.getCoefficients().subList(0, halfLength));
        Polynomial highPolynomial1 = new Polynomial(polynomial1.getCoefficients().subList(halfLength, polynomial1.getSize()));
        Polynomial lowPolynomial2 = new Polynomial(polynomial2.getCoefficients().subList(0, halfLength));
        Polynomial highPolynomial2 = new Polynomial(polynomial2.getCoefficients().subList(halfLength, polynomial2.getSize()));

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Callable<Polynomial> task1 = () -> karatsubaParallelizedForm(lowPolynomial1, lowPolynomial2, newDepth);
        Callable<Polynomial> task2 = () -> karatsubaParallelizedForm(PolynomialHelper.add(lowPolynomial1, highPolynomial1), PolynomialHelper.add(lowPolynomial2, highPolynomial2), newDepth);
        Callable<Polynomial> task3 = () -> karatsubaParallelizedForm(highPolynomial1, highPolynomial2, newDepth);

        Future<Polynomial> future1 = executor.submit(task1);
        Future<Polynomial> future2 = executor.submit(task2);
        Future<Polynomial> future3 = executor.submit(task3);

        executor.shutdown();

        Polynomial karatsuba1 = future1.get();
        Polynomial karatsuba2 = future2.get();
        Polynomial karatsuba3 = future3.get();

        executor.awaitTermination(50, TimeUnit.SECONDS);

        Polynomial polynomialResult1 = PolynomialHelper.shift(karatsuba3, 2 * halfLength);
        Polynomial polynomialResult2 = PolynomialHelper.shift(PolynomialHelper.subtract(PolynomialHelper.subtract(karatsuba2, karatsuba3), karatsuba1) , halfLength);

        return PolynomialHelper.add(PolynomialHelper.add(polynomialResult1, polynomialResult2), karatsuba3);
    }
}
