package main.java.helper;

import main.java.domain.Polynomial;

public class RegularTask implements Runnable {

    private final int start;
    private final int end;
    private final Polynomial polynomial1, polynomial2, polynomialResult;

    public RegularTask(int start, int end, Polynomial polynomial1, Polynomial polynomial2, Polynomial polynomialResult) {
        this.start = start;
        this.end = end;
        this.polynomial1 = polynomial1;
        this.polynomial2 = polynomial2;
        this.polynomialResult = polynomialResult;
    }

    @Override
    public void run() {
        for (int index = start; index < end; index++) {
            if (index > polynomialResult.getSize()) return;

            for (int i = 0; i <= index; i++)
                if (i < polynomial1.getSize() && (index - i) < polynomial2.getSize()) {
                    int value = polynomial1.getCoefficient(i) * polynomial2.getCoefficient(index - i);
                    polynomialResult.setCoefficient(index, polynomialResult.getCoefficient(index) + value);
                }
        }
    }
}
