package main.java.service;

import main.java.repository.Repository;

import java.util.Objects;

public class Consumer {

    private final Repository repository;
    private Double sum = 0.0;

    public Consumer (Repository repository) {
        this.repository = repository;

    }

    public void start() {

        try {
            while (true) {

                repository.downFill();
                {
                    Thread.sleep(100);
                    sum += repository.popBuffer();
                }
                repository.upEmpty();

                if (repository.getFill() == 0 && Objects.equals(repository.getEmpty(), repository.getBufferLength())) {
                    System.out.println("Sum = " + sum);
                    return;
                }
            }
        } catch (Exception ignore) {}
    }
}