package main.java.main;

import main.java.domain.Polynomial;
import main.java.service.Service;

public class Main {

    public static void main(String[] args) {
        Polynomial polynomial1 = new Polynomial(2);
        Polynomial polynomial2 = new Polynomial(1);

        System.out.println(polynomial1);
        System.out.println(polynomial2);
        System.out.println(Service.regularSequence(polynomial1, polynomial2));
    }
}
