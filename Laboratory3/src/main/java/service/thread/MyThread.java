package main.java.service.thread;

import main.java.domain.Element;
import main.java.helper.MatrixHelper;
import main.java.repository.Repository;

import java.util.List;

public class MyThread implements Runnable{

    private final Repository repository;
    private final List<Element> elements;

    public MyThread(Repository repository, List<Element> elements) {
        this.repository = repository;
        this.elements = elements;
    }

    @Override
    public void run() {
        elements.forEach(element -> {
            int value = MatrixHelper.computeProduct(repository.getMatrixA().getMatrix(),
                    repository.getMatrixB().getMatrix(),
                    repository.getMatrixSize(),
                    element.getLine(),
                    element.getColumn());

            repository.getMatrixC().getMatrix()[element.getLine()][element.getColumn()] = value;
        });
    }
}
