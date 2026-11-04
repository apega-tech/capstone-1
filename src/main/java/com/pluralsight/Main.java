package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

        //Color code variables
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
                    System.out.print(yellow + "⚠ " + reset);
                    System.out.println(red + "Please choose D, P, L, or X." + reset);
            }
        }
        scanner.close();
    }

    public static void addDeposit(Scanner scanner, ArrayList<Transaction> transactions){
        //Color code variables
        String pink = "\u001B[35m";
        String cyan = "\u001B[36m";
        String yellow = "\u001B[33m";
        String red = "\u001B[31m";
        String green = "\u001B[32m";
        String reset = "\u001B[0m";

        //Helper method for D
        System.out.println(pink + "\n╔═══════════════════════════════════╗");
        System.out.println("║           ADD A DEPOSIT           ║");
        System.out.println("╠═══════════════════════════════════╣" + reset);
        System.out.print(pink + "║ " + cyan + "Description:       ║" + reset);
        String description = scanner.nextLine().trim();
        System.out.print(pink + "║ " + cyan + "Vendor:      ║" + reset);
        String vendor = scanner.nextLine().trim();
        System.out.print(pink + "║ " + cyan + "Amount:      ║" + reset);
        double amount = Double.parseDouble(scanner.nextLine().trim());
        System.out.println(pink + "╚═══════════════════════════════════╝" + reset);

        //Generate date and time
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String dateStr = currentDate.format(dateFormatter);
        String timeStr = currentTime.format(timeFormatter);

        //Format CSV line
        String newLine = dateStr + "|" + timeStr + "|" + description + "|" + vendor + "|" + amount;

        //Save to transanction.csv in append mode
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true));
            writer.newLine();
            writer.write(newLine);
            writer.close();
            System.out.println(green + "✅ Deposit successfully added and saved!" + reset);
        } catch (Exception e) {
            System.out.println(red + "❌ Error saving deposit to file." + reset);
            e.printStackTrace();
        }

        //Add to ArrayList
        Transaction newTransaction = new Transaction(dateStr, timeStr, description, vendor, amount);
        transactions.add(newTransaction);
    }
}