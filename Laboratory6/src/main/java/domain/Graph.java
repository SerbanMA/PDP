package main.java.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Graph {

    private final List<List<Integer>> container;
    private final List<Integer> nodes;

    public Graph(int numberOfNodes) {
        this.container = new ArrayList<>();
        this.nodes = new ArrayList<>();

        for (int i = 1; i <= numberOfNodes; i++) {
            this.container.add(new ArrayList<>());
            this.nodes.add(i);
        }
    }

    public Graph(int numberOfNodes, int size) {
        this.container = new ArrayList<>();
        this.nodes = new ArrayList<>();

        for (int i = 0; i < numberOfNodes; i++) {
            this.container.add(new ArrayList<>());
            this.nodes.add(i);
        }

        Collections.shuffle(nodes);
        for (int i = 1; i < nodes.size(); i++) {
            addEdge(nodes.get(i - 1), nodes.get(i));
        }
        addEdge(nodes.get(getSize() - 1), nodes.get(0));

        Random random = new Random();
        for (int i = 0; i < size; i++){
            int nodeA = random.nextInt(getSize() - 1);
            int nodeB = random.nextInt(getSize() - 1);

            addEdge(nodeA, nodeB);
        }
    }

    public List<Integer> getNodes() {
        return nodes;
    }

    public void addEdge(int nodeA, int nodeB) {
        container.get(nodeA).add(nodeB);
    }

    public List<Integer> getNeighboursOf(int node) {
        return container.get(node);
    }

    public int getSize() {
        return nodes.size();
    }
}
