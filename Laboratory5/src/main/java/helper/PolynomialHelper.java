package main.java.helper;

import main.java.domain.Polynomial;

import java.util.ArrayList;
import java.util.List;

public class PolynomialHelper {

    public static Polynomial shift(Polynomial polynomial, int offset) {

        List<Integer> coefficients = new ArrayList<>(polynomial.getCoefficients());

        for (int i = 1; i <= offset; i++) {
            coefficients.add(0, 0);
        }

        return new Polynomial(coefficients);
    }

    public static Polynomial add(Polynomial polynomial1, Polynomial polynomial2) {
        int minDegree = Math.min(polynomial1.getDegree(), polynomial2.getDegree());
        int maxDegree = Math.max(polynomial1.getDegree(), polynomial2.getDegree());

        List<Integer> coefficients = new ArrayList<>();

        for (int index = 0; index <= minDegree; index++) {
            coefficients.add(polynomial1.getCoefficient(index) + polynomial2.getCoefficient(index));
        }
        addRemainingCoefficients(polynomial1, polynomial2, minDegree, maxDegree, coefficients);

        return new Polynomial(coefficients);
    }

    public static Polynomial subtract(Polynomial polynomial1, Polynomial polynomial2) {
        int minDegree = Math.min(polynomial1.getDegree(), polynomial2.getDegree());
        int maxDegree = Math.max(polynomial1.getDegree(), polynomial2.getDegree());

        List<Integer> coefficients = new ArrayList<>();

        for (int index = 0; index <= minDegree; index++) {
            coefficients.add(polynomial1.getCoefficient(index) - polynomial2.getCoefficient(index));
        }
        subtractRemainingCoefficients(polynomial1, polynomial2, minDegree, maxDegree, coefficients);

        int degree = coefficients.size() - 1;
        while(coefficients.get(degree) == 0 && degree > 0) {
            coefficients.remove(degree);
            degree--;
        }

        return new Polynomial(coefficients);
    }

    private static void addRemainingCoefficients(Polynomial polynomial1, Polynomial polynomial2, int minDegree, int maxDegree, List<Integer> coefficients) {
        if (minDegree == maxDegree) return;

        for (int index = minDegree + 1; index <= polynomial1.getDegree(); index++) {
            coefficients.add(polynomial1.getCoefficient(index));
        }

        for (int index = minDegree + 1; index <= polynomial2.getDegree(); index++) {
            coefficients.add(index, polynomial2.getCoefficient(index));
        }
    }

    private static void subtractRemainingCoefficients(Polynomial polynomial1, Polynomial polynomial2, int minDegree, int maxDegree, List<Integer> coefficients) {
        if (minDegree == maxDegree) return;

        for (int index = minDegree + 1; index <= polynomial1.getDegree(); index++) {
            coefficients.add(polynomial1.getCoefficient(index));
        }

        for (int index = minDegree + 1; index <= polynomial2.getDegree(); index++) {
            coefficients.add(index, -polynomial2.getCoefficient(index));
        }
    }
}
