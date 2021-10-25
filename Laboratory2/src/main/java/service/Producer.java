package main.java.service;

import main.java.domain.Vector;
import main.java.repository.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Producer {

    private final Repository repository;

    File myObj = new File("src/main/resources/files/input.txt");
    Scanner myReader;

    public Producer (Repository repository) {
        this.repository = repository;
        try { myReader = new Scanner(myObj); } catch (FileNotFoundException ignore) {}
    }

    public void start() {

        try {
            while (myReader.hasNextLine()) {

                repository.downEmpty();
                {
                    String line = myReader.nextLine();
                    if (line.equals("End"))
                        throw new InterruptedException();

                    String[] data = line.split(" ");
                    Vector vector1 = new Vector(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
                    Vector vector2 = new Vector(Double.parseDouble(data[2]), Double.parseDouble(data[3]));

                    System.out.println(vector1 + " * " + vector2);

                    repository.pushBuffer(vector1.getScalarProduct(vector2));
                }
                repository.upFill();

            }
        } catch (InterruptedException ignore) {}
    }
}

