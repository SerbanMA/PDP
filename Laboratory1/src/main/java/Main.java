package main.java;

import main.java.domain.Node;
import main.java.service.Service;
import main.java.repository.Repository;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {

        Random random = new Random();

        Repository repository = new Repository();
        Service service = new Service(repository);

        try {

            service.addNodes();
            ArrayList<Integer> leavesId = service.getLeavesId();

            repository.getNodeById(0).setLeaf();
            leavesId.forEach(nodeId -> repository.getNodeById(nodeId).setLeaf());

            while (true) {

                Integer nodeId = leavesId.get(random.nextInt(leavesId.size()));
                Integer newValue = random.nextInt(100);

                CompletableFuture.runAsync(() -> {

                    try {
                        service.start(nodeId, newValue);
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage());
                    }
                });

                if (random.nextInt(100) == 0) {
                    service.verify();
                    System.out.println("Result is ok");
                }

                Thread.sleep(10);
            }
        } catch (Exception exception) { System.out.println(exception.getMessage()); }

    }
}
