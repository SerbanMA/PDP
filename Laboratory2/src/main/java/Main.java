package main.java;


import main.java.repository.Repository;
import main.java.service.Consumer;
import main.java.service.Producer;

import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {

        Repository repository = new Repository();

        Producer producer = new Producer(repository);
        Consumer consumer = new Consumer(repository);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(producer::start),
                CompletableFuture.runAsync(consumer::start)
        ).join();
    }


}
