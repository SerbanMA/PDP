package main.java.repository;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Repository {

    private final Queue<Double> buffer = new LinkedList<>() {};
    private final Semaphore fill = new Semaphore(0);
    private final Semaphore empty = new Semaphore(11);

    public synchronized Integer getBufferLength() {
        return 10;
    }

    public Integer getFill() {
        return fill.availablePermits();
    }

    public Integer getEmpty() {
        return empty.availablePermits();
    }

    public synchronized void pushBuffer(Double value) {
        buffer.add(value);
    }

    public synchronized Double popBuffer() {
        return buffer.remove();
    }

    public void upFill () {
        fill.release();
    }

    public void upEmpty () {
        empty.release();
    }

    public void downFill () throws InterruptedException {
        fill.acquire();
    }

    public void downEmpty () throws InterruptedException {
        empty.acquire();
    }
}
