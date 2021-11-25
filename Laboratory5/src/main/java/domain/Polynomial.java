package main.java.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Polynomial {

    private static final Integer BOUND = 10;
    private final Integer degree;
    private final List<Integer> coefficients;

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = coefficients;
        degree = coefficients.size() - 1;
    }

    public Polynomial(Integer degree) {
        this.degree = degree;
        coefficients = new ArrayList<>(degree + 1);
        Random random = new Random();
        for (int i = 0; i < degree; i++) {
            coefficients.add(random.nextInt(BOUND));
        }
        coefficients.add(random.nextInt(1, BOUND));
    }

    public Polynomial clear() {
        for (int i = 0; i <= degree; i++) {
            coefficients.set(i, 0);
        }
        return this;
    }

    public Integer getDegree() {
        return degree;
    }

    public Integer getCoefficient(Integer index) {
        return coefficients.get(index);
    }

    public void setCoefficient(Integer index, Integer value) {
        coefficients.set(index, value);
    }

    public Integer getSize() {
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
