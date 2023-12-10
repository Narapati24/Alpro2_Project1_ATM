package bank;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ATM {
    private static void readData() {
        try {
            File file = new File("src/data.txt");
            Scanner sc = new Scanner(file);
            
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(line);
            }
            
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        readData();
    }
}
