package main.java.domain;

import java.util.concurrent.Semaphore;

public class Node {

    private final Integer id;
    private final Integer parentId;
    private Integer value;
    private Boolean isLeaf;

    private final Semaphore semaphore = new Semaphore(1, true);

    public Node() {
        this.id = 0;
        this.parentId = 0;
        this.value = 0;
        this.isLeaf = false;
    }

    public Node(Integer id, Integer parentId, Integer value) {
        this.id = id;
        this.parentId = parentId;
        this.value = value;
        this.isLeaf = false;
    }

    public Integer getId() {
        return id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public Integer getValue() {
        return value;
    }

    public void addValue(Integer value) {
        this.value += value;
    }

    public Boolean isIsLeaf() {
        return isLeaf;
    }

    public void setLeaf() {
        this.isLeaf = true;
    }

    public void acquireAccess() throws InterruptedException {
        semaphore.acquire();
    }

    public void releaseAccess() {
        semaphore.release();
    }

    @Override
    public String toString() {
        return "{" + id + "}" + " : " + value;
    }
}
