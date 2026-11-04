package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        //ArrayList to store the transaction
        ArrayList<Transaction> transactions = new ArrayList<>();
        //Read the CSV when the app starts
        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
            String line;

            while ((line = reader.readLine()) !=null){
                String[] parts = line.split("\\|");

                String date = parts[0];
                String time = parts[1];
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                transaction.add(transaction);
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading file!");
        }

        //Home screen
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // ANSI color code variables
        String pink = "\u001B[35m";
        String cyan = "\u001B[36m";
        String yellow = "\u001B[33m";
        String red = "\u001B[31m";
        String reset = "\u001B[0m";

        while (running){
            System.out.println(pink + "\n╔═══════════════════════════════════╗");
            System.out.println("║            HOME SCREEN            ║");
            System.out.println("╠═══════════════════════════════════╣");
            System.out.println("║  " + cyan + "D)" + pink + " Add Deposit                   ║");
            System.out.println("║  " + cyan + "P)" + pink + " Make Payment (Debit)          ║");
            System.out.println("║  " + cyan + "L)" + pink + " Ledger                        ║");
            System.out.println("║  " + cyan + "X)" + pink + " Exit                          ║");
            System.out.println("╚═══════════════════════════════════╝" + reset);

            System.out.print(cyan + "Choose an option: " + reset);

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice){
                case "D":
                    System.out.println(yellow + "-> Add Deposit selected: " + reset);
                    break;

                case "P":
                    System.out.println(yellow + "-> Make Payment selected: " + reset);
                    break;

                case "L":
                    System.out.println(yellow + "-> Ledger screen selected: " + reset);
                    break;

                case "X":
                    System.out.println(yellow + "-> Exiting application. Bye Bye!" + reset);
                    break;

                default:
                    System.out.println(red + "⚠\uFE0F Please choose D, P, L, or X." + reset);
            }
        }
        scanner.close();
    }
}
