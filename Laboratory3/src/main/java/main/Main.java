package main.java.main;

import main.java.domain.Matrix;
import main.java.repository.Repository;
import main.java.service.Service;

import java.util.Date;

public class Main {

    public static void main(String[] args) {

        int size = 2000;
        int noThreads = 20;

        Matrix matrixA = new Matrix(size).computeRandom();
        Matrix matrixB = new Matrix(size).computeRandom();
        Matrix matrixC = new Matrix(size);

        Repository repository = new Repository(matrixA, matrixB, matrixC, size);

        Service service = new Service(repository, noThreads);
        {
            long start = new Date().getTime();
            service.runThreadsOnLine();
            long finish = new Date().getTime();

            System.out.println("Single Thread (line) : " + (finish - start));
        }
        {
            long start = new Date().getTime();
            service.runThreadsOnColumn();
            long finish = new Date().getTime();

            System.out.println("Single Thread (column) : " + (finish - start));
        }
        {
            long start = new Date().getTime();
            service.runThreadsOnKth();
            long finish = new Date().getTime();

            System.out.println("Single Thread (k-th) : " + (finish - start));
        }
        System.out.println();
        {
            long start = new Date().getTime();
            service.runPoolOnLine();
            long finish = new Date().getTime();

            System.out.println("Pool Thread (line) : " + (finish - start));
        }
        {
            long start = new Date().getTime();
            service.runPoolOnColumn();
            long finish = new Date().getTime();

            System.out.println("Pool Thread (column) : " + (finish - start));
        }
        {
            long start = new Date().getTime();
            service.runPoolOnKth();
            long finish = new Date().getTime();

            System.out.println("Pool Thread (k-th) : " + (finish - start));
        }
    }
}
