package main.java.service;

import main.java.domain.Node;
import main.java.helper.DataReader;
import main.java.repository.Repository;

import java.util.*;
import java.util.stream.Collectors;

public class Service {

    private final Repository repository;
    private final DataReader dataReader;

    public Service(Repository repository) {
        this.repository = repository;
        this.dataReader = new DataReader(repository);
    }

    public void start(Integer nodeId, Integer newValue) throws Exception {

        HashMap<Integer, Node> nodes = repository.getNodes();
        ArrayList<Node> parents = new ArrayList<>();

        Node node = getNodeById(nodeId);
        Integer alpha = newValue - node.getValue();

        try {
            while (node.getId() != 0) {
                parents.add(node);
                node.acquireAccess();
                node = getNodeById(node.getParentId());
            }

        } catch (InterruptedException e) {
            parents.forEach(Node::releaseAccess);
            throw new Exception("Ups, some thread error!");
        }

        parents.forEach(parentNode -> {
            parentNode.addValue(alpha);
            parentNode.releaseAccess();
        });
    }

    public void verify() throws Exception {
        System.out.println(repository.getNodes());

        for(Node node : repository.getNodes().values()) {
            if (!node.isIsLeaf()) {
                Integer value = repository.getNodes().values()
                        .stream().filter(childNode -> Objects.equals(childNode.getParentId(), node.getId()))
                        .map(Node::getValue)
                        .reduce(0, Integer::sum);
                System.out.println(node.getId() + " -> " + value);

                if (!Objects.equals(value, node.getValue()))
                    throw new Exception("Result is not ok");
            }
        }
    }

    public void addNodes() throws Exception {
        dataReader.getNodes().forEach(repository::addNode);
    }

    public ArrayList<Integer> getLeavesId() throws Exception {

        List<Integer> ids = repository.getNodes().keySet()
                .stream()
                .distinct()
                .collect(Collectors.toList());

        List<Integer> parentIds = repository.getNodes().values()
                .stream()
                .map(Node::getParentId)
                .distinct()
                .collect(Collectors.toList());

        return ids.stream()
                .filter(nodeId -> !parentIds.contains(nodeId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Node getNodeById(Integer id) {
        return repository.getNodeById(id);
    }
}
