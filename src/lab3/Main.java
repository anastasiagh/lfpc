package lab3;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            CNF chomsky = new CNF(new File("production.txt"));
            chomsky.to_chomsky();
        }
        catch (FileNotFoundException e){
            System.out.print(e.toString());
        }
    }
}