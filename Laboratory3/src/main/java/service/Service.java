package main.java.service;

import main.java.domain.Element;
import main.java.helper.MatrixHelper;
import main.java.repository.Repository;
import main.java.service.thread.MyThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Service {

    private final Repository repository;
    private final int noThreads;

    public Service(Repository repository, int noThreads) {
        this.repository = repository;
        this.noThreads = noThreads;
    }

    public void runThreadsOnLine() {
        List<Thread> threads = new ArrayList<>();
        List<Element> elements = new ArrayList<>();

        int matrixSize = repository.getMatrixSize();
        int noElements = matrixSize * matrixSize;
        int noElementsPerThread = noElements / noThreads;

        for (int i = 0; i < noThreads; i++) {

            int start = (noElementsPerThread * i) + 1;
            int end = noElementsPerThread * (i + 1);

            if (i == noThreads - 1) end = noElements;

            for (int position = start; position <= end; position++) {
                elements.add(MatrixHelper.getElementByPosition(matrixSize, position));
            }
            threads.add(new Thread(new MyThread(repository, elements)));
            threads.get(i).start();

            elements = new ArrayList<>();
        }

        try {
            for (int i = 0; i < noThreads; i++) {
                threads.get(i).join();
            }
        } catch (InterruptedException e) {
            System.err.println("Exception: " + e);
        }
    }

    public void runThreadsOnColumn() {
        List<Thread> threads = new ArrayList<>();
        List<Element> elements = new ArrayList<>();

        int matrixSize = repository.getMatrixSize();
        int noElements = matrixSize * matrixSize;
        int noElementsPerThread = noElements / noThreads;

        for (int i = 0; i < noThreads; i++) {

            int start = (noElementsPerThread * i) + 1;
            int end = noElementsPerThread * (i + 1);

            if (i == noThreads - 1) end = noElements;

            for (int position = start; position <= end; position++) {
                elements.add(MatrixHelper.getOppositeElementByPosition(matrixSize, position));
            }
            threads.add(new Thread(new MyThread(repository, elements)));
            threads.get(i).start();

            elements = new ArrayList<>();
        }

        try {
            for (int i = 0; i < noThreads; i++) {
                threads.get(i).join();
            }
        } catch (InterruptedException e) {
            System.err.println("Exception: " + e);
        }
    }

    public void runThreadsOnKth() {
        List<Thread> threads = new ArrayList<>();
        List<Element> elements = new ArrayList<>();

        int matrixSize = repository.getMatrixSize();
        int noElements = matrixSize * matrixSize;

        for (int i = 0; i < noThreads; i++) {

            for (int position = 1; position <= noElements; position++) {
                if (position % noThreads == i)
                    elements.add(MatrixHelper.getElementByPosition(matrixSize, position));
            }
            threads.add(new Thread(new MyThread(repository, elements)));
            threads.get(i).start();

            elements = new ArrayList<>();
        }

        try {
            for (int i = 0; i < noThreads; i++) {
                threads.get(i).join();
            }
        } catch (InterruptedException e) {
            System.err.println("Exception: " + e);
        }
    }

    public void runPoolOnLine() {
        ExecutorService executor = Executors.newFixedThreadPool(noThreads);
        List<Element> elements = new ArrayList<>();

        int matrixSize = repository.getMatrixSize();
        int noElements = matrixSize * matrixSize;
        int noElementsPerThread = noElements / noThreads;

        for (int i = 0; i < noThreads; i++) {

            int start = (noElementsPerThread * i) + 1;
            int end = noElementsPerThread * (i + 1);

            if (i == noThreads - 1) end = noElements;

            for (int position = start; position <= end; position++) {
                elements.add(MatrixHelper.getElementByPosition(matrixSize, position));
            }
            executor.execute(new Thread(new MyThread(repository, elements)));

            elements = new ArrayList<>();
        }

        executor.shutdown();
        while (!executor.isTerminated()) {}
    }

    public void runPoolOnColumn() {
        ExecutorService executor = Executors.newFixedThreadPool(noThreads);
        List<Element> elements = new ArrayList<>();

        int matrixSize = repository.getMatrixSize();
        int noElements = matrixSize * matrixSize;
        int noElementsPerThread = noElements / noThreads;

        for (int i = 0; i < noThreads; i++) {

            int start = (noElementsPerThread * i) + 1;
            int end = noElementsPerThread * (i + 1);

            if (i == noThreads - 1) end = noElements;

            for (int position = start; position <= end; position++) {
                elements.add(MatrixHelper.getOppositeElementByPosition(matrixSize, position));
            }
            executor.execute(new Thread(new MyThread(repository, elements)));

            elements = new ArrayList<>();
        }

        executor.shutdown();
        while (!executor.isTerminated()) {}
    }

    public void runPoolOnKth() {
        ExecutorService executor = Executors.newFixedThreadPool(noThreads);
        List<Element> elements = new ArrayList<>();

        int matrixSize = repository.getMatrixSize();
        int noElements = matrixSize * matrixSize;

        for (int i = 0; i < noThreads; i++) {

            for (int position = 1; position <= noElements; position++) {
                if (position % noThreads == i)
                    elements.add(MatrixHelper.getElementByPosition(matrixSize, position));
            }
            executor.execute(new Thread(new MyThread(repository, elements)));

            elements = new ArrayList<>();
        }

        executor.shutdown();
        while (!executor.isTerminated()) {}
    }
}
