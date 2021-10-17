package main.java.helper;

import main.java.domain.Node;
import main.java.repository.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DataReader {

    private final Repository repository;

    public DataReader(Repository repository) {
        this.repository = repository;
    }

    public ArrayList<Node> getNodes() throws Exception {

        ArrayList<Node> nodes = new ArrayList<>();
        try {
            File myObj = new File("src/files/input.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();

                String[] data =  line.split(" ");

                if (data.length != 3)
                    throw new ArrayIndexOutOfBoundsException();

                nodes.add(new Node(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])));
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            throw new Exception("File not found.");
        } catch (NumberFormatException e) {
            throw new Exception("File contains non-numerical values");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new Exception("File needs to have 3 values on every line");
        }

        return nodes;
    }
}
