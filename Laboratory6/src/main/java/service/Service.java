package main.java.service;

import main.java.domain.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Service {

    public static void findHamiltonian(Graph graph, int numberOfThreads) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(numberOfThreads);
        Lock lock = new ReentrantLock();

        for (int i = 0; i < graph.getSize(); i++){
            pool.execute(new FindTask(graph, i, lock));
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
    }
}
