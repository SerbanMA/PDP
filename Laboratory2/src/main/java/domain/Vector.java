package main.java.domain;

public class Vector {

    private final Double i;
    private final Double j;

    public Vector () {
        this.i = 0.0;
        this.j = 0.0;
    }

    public Vector (Double i, Double j) {
        this.i = i;
        this.j = j;
    }

    public Double getI() {
        return i;
    }

    public Double getJ() {
        return j;
    }

    public Double getScalarProduct (Vector v) {
        return this.i * v.getI() + this.j * v.getJ();
    }

    @Override
    public String toString() {
        return "vector( " +
                + i +
                ", " + j +
                " )";
    }
}
