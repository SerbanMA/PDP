package main.java.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Polynomial {

    private static final int BOUND = 10;
    private final List<Integer> coefficients;

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial(int degree) {
        coefficients = new ArrayList<>(degree + 1);
        Random random = new Random();
        for (int i = 0; i <= degree; i++) {
            coefficients.add(random.nextInt(-BOUND, BOUND));
        }
        while (coefficients.get(degree) == 0)
            coefficients.set(degree, random.nextInt(-BOUND, BOUND));
    }

    public Polynomial clear() {
        for (int i = 0; i <= coefficients.size() - 1; i++) {
            coefficients.set(i, 0);
        }
        return this;
    }

    public int getDegree() {
        return coefficients.size() - 1;
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    public int getCoefficient(int index) {
        return coefficients.get(index);
    }

    public void setCoefficient(int index, int value) {
        coefficients.set(index, value);
    }

    public int getSize() {
        return coefficients.size();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        int power = getDegree();
        for (int i = getDegree(); i >= 0; i--) {
            if (coefficients.get(i) == 0)
                continue;
            str.append(coefficients.get(i)).append("x^").append(power).append(" + ");
            power--;
        }
        return str.substring(0, str.length() - 3);
    }
}
