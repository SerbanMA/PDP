package main.java.service;

import main.java.domain.Polynomial;
import main.java.helper.RegularTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
}
