package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
            String line;

            while ((line = reader.readLine()) !=null){
                System.out.println(line);
            }
            reader.close();

        } catch (Exception e) {
            System.out.println("Error reading file!");
        }
    }
}
