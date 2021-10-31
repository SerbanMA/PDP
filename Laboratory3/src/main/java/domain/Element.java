package main.java.domain;

public class Element {

    private final int line;
    private final int column;

    public Element(int line, int column) {
        this.line = line;
        this.column =  column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "Element{" +
                "line=" + line +
                ", column=" + column +
                '}';
    }
}
