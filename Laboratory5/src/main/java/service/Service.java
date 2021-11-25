package main.java.service;

import main.java.domain.Polynomial;

public class Service {

    public static Polynomial regularSequence(Polynomial polynomial1, Polynomial polynomial2) {
        Integer polynomialResultDegree = polynomial1.getDegree() + polynomial2.getDegree();
        Polynomial polynomialResult = new Polynomial(polynomialResultDegree).clear();

        for (int i = 0; i < polynomial1.getSize(); i++)
            for (int j = 0; j < polynomial2.getSize(); j++) {
                int index = i + j;
                Integer value = polynomial1.getCoefficient(i) * polynomial2.getCoefficient(j);

                polynomialResult.setCoefficient(index, polynomialResult.getCoefficient(index) + value);
            }

        return polynomialResult;
    }
}
