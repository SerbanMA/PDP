package main.java.main;

import main.java.domain.Graph;
import main.java.test.Test;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int NUMBER_OF_GRAPHS = 101;

    public static void main(String[] args) throws InterruptedException {
        List<Graph> graphs = new ArrayList<>();

        for (int i = 1; i <= NUMBER_OF_GRAPHS; i++) {
            graphs.add(new Graph(i*10, i*10/2));
        }

        System.out.println("Sequential search");
        Test.batchTesting(graphs, 1);

        System.out.println("Parallel search");
        Test.batchTesting(graphs, 4);
    }
}
