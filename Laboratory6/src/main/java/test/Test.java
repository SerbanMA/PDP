package main.java.test;

import main.java.domain.Graph;
import main.java.service.Service;

import java.util.List;

public class Test {

    public static void batchTesting(List<Graph> graphs, int numberOfThreads) throws InterruptedException {
        for (int i = 0; i < graphs.size(); i+=25) {
            test(i, graphs.get(i), numberOfThreads);
        }
    }

    private static void test(int level, Graph graph, int numberOfThreads) throws  InterruptedException {
        long startTime = System.currentTimeMillis();
        Service.findHamiltonian(graph, numberOfThreads);
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        System.out.println("Level " + level + ": " + duration + " ms");
    }
}
