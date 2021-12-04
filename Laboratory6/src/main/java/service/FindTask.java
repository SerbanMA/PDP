package main.java.service;

import main.java.domain.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class FindTask implements Runnable{

    private final Graph graph;
    private final int startingNode;
    private final List<Integer> path;

    private final List<Integer> result;
    private final Lock lock;

    public FindTask(Graph graph, int startingNode, Lock lock) {
        this.graph = graph;
        this.startingNode = startingNode;
        this.path = new ArrayList<>();
        this.result = new ArrayList<>();
        this.lock = lock;
    }

    private List<Integer> getResult() {
        return result;
    }

    @Override
    public void run() {
        run(startingNode);
    }

    private void run(int node) {
        path.add(node);

        if (path.size() == graph.getSize()) {
            if (graph.getNeighboursOf(node).contains(startingNode)) {
                setResult();
            }
            return;
        }

        for (int neighbour : graph.getNeighboursOf(node)) {
            if (!path.contains(neighbour)){
                run(neighbour);
            }
        }
    }

    private void setResult() {
        lock.lock();
        result.clear();
        result.addAll(path);
        lock.unlock();
    }
}
