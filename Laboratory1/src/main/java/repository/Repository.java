package main.java.repository;

import main.java.domain.Node;

import java.util.*;

public class Repository {

    private HashMap<Integer, Node> nodes = new HashMap<>();

    public Repository() {}

    public HashMap<Integer, Node> getNodes() {
        return nodes;
    }

    public void setNodes(HashMap<Integer, Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
    }

    public Node getNodeById(Integer id) {
        return nodes.get(id);
    }
}
