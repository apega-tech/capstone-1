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
    public static void main(String[] args) {
        //ArrayList to store the transaction
        ArrayList<Transaction> transactions = new ArrayList<>();
        //Read the CSV when the app starts
        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");

                String date = parts[0];
                String time = parts[1];
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                transactions.add(transaction);
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading file!");
        }

        //Home screen menu
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        //Color code variables
        String pink = "\u001B[95m";
        String cyan = "\u001B[36m";
        String yellow = "\u001B[33m";
        String red = "\u001B[31m";
        String reset = "\u001B[0m";

        while (running) {
            System.out.println(pink + "\n╔═══════════════════════════════════╗");
            System.out.println("║            " + cyan + "HOME SCREEN" + pink+ "            ║");
            System.out.println("╠═══════════════════════════════════╣");
            System.out.println("║  " + cyan + "D)" + pink + " Add Deposit                   ║");
            System.out.println("║  " + cyan + "P)" + pink + " Make Payment (Debit)          ║");
            System.out.println("║  " + cyan + "L)" + pink + " Ledger                        ║");
            System.out.println("║  " + cyan + "X)" + pink + " Exit                          ║");
            System.out.println("╚═══════════════════════════════════╝" + reset);

            System.out.print(cyan + "Choose an option: " + reset);

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "D":
                    addDeposit(scanner, transactions);
                    break;

                case "P":
                    makePayment(scanner, transactions);
                    break;

                case "L":
                    showLedger(scanner, transactions);
                    break;

                case "X":
                    System.out.println(yellow + "-> Exiting application. Bye Bye!" + reset);
                    running = false;
                    break;

                default:
                    System.out.print(yellow + "⚠ " + reset);
                    System.out.println(red + "Please choose from D, P, L, or X." + reset);
            }
        }
        scanner.close();
    }

    //Method for D
    public static void addDeposit(Scanner scanner, ArrayList<Transaction> transactions) {
        //Color code variables
        String pink = "\u001B[95m";
        String cyan = "\u001B[36m";
        String red = "\u001B[31m";
        String green = "\u001B[32m";
        String reset = "\u001B[0m";

        System.out.println(pink + "\n╔═════════════════════════════════════╗");
        System.out.println("║            " + cyan + "ADD A DEPOSIT" + pink+ "            ║");
        System.out.println(pink + "╠═════════════════════════════════════╣");
        System.out.print(cyan + "Description: " + reset);
        String description = scanner.nextLine().trim();

        System.out.print(cyan + "Vendor: " + reset);
        String vendor = scanner.nextLine().trim();

        System.out.println(cyan + "Amount: " + reset);
        double amount = Double.parseDouble(scanner.nextLine().trim());

        // Format the amount into a String with 2 decimal
        String formattedAmount = String.format("%.2f", amount);

        //Generate date and time
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String dateStr = currentDate.format(dateFormatter);
        String timeStr = currentTime.format(timeFormatter);

        //Format CSV line
        String newLine = dateStr + "|" + timeStr + "|" + description + "|" + vendor + "|" + formattedAmount;

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

    //Method for P
    public static void makePayment(Scanner scanner, ArrayList<Transaction> transactions) {
        String pink = "\u001B[95m";
        String cyan = "\u001B[36m";
        String red = "\u001B[31m";
        String green = "\u001B[32m";
        String reset = "\u001B[0m";

        System.out.println(pink + "\n╔═══════════════════════════════════╗");
        System.out.println("║          " + cyan + "MAKE A PAYMENT" + pink+ "           ║");
        System.out.println("╠═══════════════════════════════════╣");
        System.out.print(cyan + "Description: " + reset);
        String description = scanner.nextLine().trim();

        System.out.print(cyan + "Vendor: " + reset);
        String vendor = scanner.nextLine().trim();

        System.out.println(cyan + "Amount: " + reset);
        double rawAmount = Double.parseDouble(scanner.nextLine().trim());

        //Save the payment as a negative number
        double amount = -Math.abs(rawAmount);

        // Format the amount into a String with 2 decimal
        String formattedAmount = String.format("%.2f", amount);

        //Generate date and time
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String dateStr = currentDate.format(dateFormatter);
        String timeStr = currentTime.format(timeFormatter);

        //Format CSV line
        String newLine = dateStr + "|" + timeStr + "|" + description + "|" + vendor + "|" + formattedAmount;

        //Save to transactions.csv in append mode
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true));
            writer.newLine();
            writer.write(newLine);
            writer.close();
            System.out.println(green + "✅ Payment successfully processed and saved!" + reset);
        } catch (Exception e) {
            System.out.println(red + "❌ Error saving payment to file." + reset);
            e.printStackTrace();
        }

        // Add to ArrayList
        Transaction newTransaction = new Transaction(dateStr, timeStr, description, vendor, amount);
        transactions.add(newTransaction);
    }

    //Method for L
    public static void showLedger(Scanner scanner, ArrayList<Transaction> transactions){
        String pink = "\u001B[95m";
        String cyan = "\u001B[36m";
        String yellow = "\u001B[33m";
        String red = "\u001b[31m";
        String reset = "\u001B[0m";

        boolean ledgerRunning = true;

        while (ledgerRunning) {
            System.out.println(pink + "\n╔═══════════════════════════════════╗");
            System.out.println("║            " + cyan + "LEDGER MENU" + pink+ "            ║");
            System.out.println("╠═══════════════════════════════════╣");
            System.out.println("║  " + cyan + "A)" + pink + " All Transactions              ║");
            System.out.println("║  " + cyan + "D)" + pink + " Deposits Only                 ║");
            System.out.println("║  " + cyan + "P)" + pink + " Payments Only                 ║");
            System.out.println("║  " + cyan + "R)" + pink + " Reports                       ║");
            System.out.println("║  " + cyan + "H)" + pink + " Home (Main Menu)              ║");
            System.out.println("╚═══════════════════════════════════╝" + reset);

            System.out.print(cyan + "Choose a Ledger option: " + reset);
            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice){
                case "A":
                    displayAllTransactions(transactions);
                    break;

                case "D":
                    displayDeposits(transactions);
                    break;

                case "P":
                    displayPayments(transactions);
                    break;

                case "R":
                    showReportsMenu(transactions, scanner);
                    break;

                case "H":
                    ledgerRunning = false;
                    break;

                default:
                    System.out.print(yellow + "⚠ " + reset);
                    System.out.println(red + "Please choose from A, D, P, R or H." + reset);
            }
        }
    }

    //Method to display all transactions
    public static void displayAllTransactions(ArrayList<Transaction> transactions){
        String pink = "\u001B[95m";
        String cyan = "\u001B[36m";
        String red = "\u001B[31m";
        String reset = "\u001B[0m";

        System.out.println(pink + "\n╔═══════════════════════════════════╗");
        System.out.println("║         " + cyan + "ALL TRANSACTIONS" + pink+ "          ║");
        System.out.println("╠═══════════════════════════════════╣");

        if (transactions.isEmpty()){
            System.out.println(red + "No transactions found." + reset);
            return;
        }
        //Loop and print each transaction
        for (int i = transactions.size() - 1; i >= 0; i--){
            Transaction t = transactions.get(i);
            String formattedAmount = String.format("%.2f", t.getAmount());

            System.out.println(cyan + "Date: " + reset + t.getDate());
            System.out.println(cyan + " | Time: " + reset + t.getTime());
            System.out.println(cyan + " | Description: " + reset + t.getDescription());
            System.out.println(cyan + " | Vendor: " + reset + t.getVendor());
            System.out.println(cyan + " | Amount: " + reset + formattedAmount);
        }
    }

    //Method to display only deposits (positive amount)
    public static void displayDeposits(ArrayList<Transaction> transactions) {
        String pink = "\u001B[95m";
        String cyan = "\u001B[36m";
        String red = "\u001B[31m";
        String reset = "\u001B[0m";

        System.out.println(pink + "\n╔═══════════════════════════════════╗");
        System.out.println("║           " + cyan + "DEPOSITS ONLY" + pink+ "           ║");
        System.out.println("╠═══════════════════════════════════╣");

        boolean found = false;
        for (int i = transactions.size() - 1; i >= 0; i--){
            Transaction t = transactions.get(i);
            if (t.getAmount() > 0){
                String formattedAmount = String.format("%.2f", t.getAmount());

                System.out.println(cyan + "Date: " + reset + t.getDate());
                System.out.println(cyan + " | Time: " + reset + t.getTime());
                System.out.println(cyan + " | Description: " + reset + t.getDescription());
                System.out.println(cyan + " | Vendor: " + reset + t.getVendor());
                System.out.println(cyan + " | Amount: " + reset + formattedAmount);
                found = true;
            }
        }
        if (!found){
            System.out.println(red + "No deposits found." + reset);
        }
    }

    //Method to display only payments (negative amount)
    public static void displayPayments(ArrayList<Transaction> transactions) {
        String pink = "\u001B[95m";
        String cyan = "\u001B[36m";
        String red = "\u001B[31m";
        String reset = "\u001B[0m";

        System.out.println(pink + "\n╔═══════════════════════════════════╗");
        System.out.println("║           " + cyan + "PAYMENTS ONLY" + pink+ "           ║");
        System.out.println("╠═══════════════════════════════════╣");

        boolean found = false;
        for (int i = transactions.size() - 1; i >= 0; i--){
            Transaction t = transactions.get(i);
            if (t.getAmount() < 0){
                String formattedAmount = String.format("%.2f", t.getAmount());

                System.out.println(cyan + "Date: " + reset + t.getDate());
                System.out.println(cyan + " | Time: " + reset + t.getTime());
                System.out.println(cyan + " | Description: " + reset + t.getDescription());
                System.out.println(cyan + " | Vendor: " + reset + t.getVendor());
                System.out.println(cyan + " | Amount: " + reset + formattedAmount);
                found = true;
            }
        }
        if (!found) {
            System.out.println(red + "No payments found." + reset);
        }
    }

    //Method to show reports menu
    public static void showReportsMenu(ArrayList<Transaction> transactions, Scanner scanner){
        String pink = "\u001B[95m";
        String cyan = "\u001B[36m";
        String red = "\u001b[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";

        boolean reportsRunning = true;

        while (reportsRunning){
            System.out.println(pink + "\n╔═══════════════════════════════════╗");
            System.out.println("║            " + cyan + "REPORTS MENU" + pink+ "           ║");
            System.out.println("╠═══════════════════════════════════╣");
            System.out.println("║  " + cyan + "1)" + pink + " Month To Date                 ║");
            System.out.println("║  " + cyan + "2)" + pink + " Previous Month                ║");
            System.out.println("║  " + cyan + "3)" + pink + " Year To Date                  ║");
            System.out.println("║  " + cyan + "4)" + pink + " Previous Year                 ║");
            System.out.println("║  " + cyan + "5)" + pink + " Search by Vendor              ║");
            System.out.println("║  " + cyan + "0)" + pink + " Back (To Ledger Menu)         ║");
            System.out.println("╚═══════════════════════════════════╝" + reset);

            System.out.print(cyan + "Choose a report option: " + reset);
            String choice = scanner.nextLine().trim();

            switch (choice){
                case "1":
                    displayMonthToDate(scanner, transactions);
                    break;
                case "2":
                    displayPreviousMonth(scanner, transactions);
                    break;
                case "3":
                    displayYearToDate(scanner, transactions);
                    break;
                case "4":
                    displayPreviousYear(scanner, transactions);
                    break;
                case "5":
                    searchByVendor(scanner, transactions);
                    break;
                case "0":
                    reportsRunning = false;
                    break;
                default:
                    System.out.print(yellow + "⚠ " + reset);
                    System.out.println(red + "Please choose from 0-5." + reset);
            }
        }
    }

    //Method to search vendor
    public static void searchByVendor(Scanner scanner, ArrayList<Transaction> transactions){
        String pink = "\u001B[95m";
        String cyan = "\u001B[36m";
        String red = "\u001B[31m";
        String reset = "\u001B[0m";

        System.out.print(cyan + "Enter vendor name to search: " + reset);
        String targetVendor = scanner.nextLine().trim();

        System.out.println(pink + "\n╔═══════════════════════════════════╗");
        System.out.println("║ " + cyan + "TRANSACTIONS FOR VENDOR: "  + targetVendor + pink+ "   ║");
        System.out.println("╠═══════════════════════════════════╣");

        boolean found = false;
        for (Transaction t : transactions){
            if (t.getVendor().equalsIgnoreCase(targetVendor)){
                // Format the amount to always show 2 decimal places
                String formattedAmount = String.format("%.2f", t.getAmount());

                System.out.println(cyan + "Date: " + reset + t.getDate());
                System.out.println(cyan + " | Time: " + reset + t.getTime());
                System.out.println(cyan + " | Description: " + reset + t.getDescription());
                System.out.println(cyan + " | Vendor: " + reset + t.getVendor());
                System.out.println(cyan + " | Amount: " + reset + formattedAmount);
                found = true;
            }
        }

        if (!found){
            System.out.println(red + "No transactions found for vendor " + targetVendor + reset);
        }
    }

    //Method for month to date
    public static void displayMonthToDate(Scanner scanner, ArrayList<Transaction> transactions){
        String pink = "\u001B[95m";
        String cyan = "\u001B[36m";
        String red = "\u001B[31m";
        String reset = "\u001B[0m";

        LocalDate now = LocalDate.now();
        String currentYearMonth = now.getYear() + "-" + String.format("%02d", now.getMonthValue());

        System.out.println(pink + "\n╔═══════════════════════════════════╗");
        System.out.println("║      " + cyan + "MONTH TO DATE REPORTS" + pink+ "        ║");
        System.out.println("╠═══════════════════════════════════╣");
        boolean found = false;

        for (Transaction t : transactions){
            if (t.getDate().startsWith(currentYearMonth)){
                String formattedAmount = String.format("%.2f", t.getAmount());

                System.out.println(cyan + "Date: " + reset + t.getDate());
                System.out.println(cyan + " | Time: " + reset + t.getTime());
                System.out.println(cyan + " | Description: " + reset + t.getDescription());
                System.out.println(cyan + " | Vendor: " + reset + t.getVendor());
                System.out.println(cyan + " | Amount: " + reset + formattedAmount);
                found = true;
            }
        }
        if (!found){
            System.out.println(red + "No transactions found for this month." + reset);
        }
    }

    // Method for previous month
    public static void displayPreviousMonth(Scanner scanner, ArrayList<Transaction> transactions){
        String pink = "\u001B[95m";
        String cyan = "\u001B[36m";
        String red = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";

        // Get the target year and month numbers for last month
        LocalDate prevMonth = LocalDate.now().minusMonths(1);
        int targetYear = prevMonth.getYear();
        int targetMonth = prevMonth.getMonthValue();

        System.out.println(pink + "\n╔═══════════════════════════════════╗");
        System.out.println("║        " + cyan + "PREVIOUS MONTH REPORTS" + pink+ "     ║");
        System.out.println("╠═══════════════════════════════════╣");

        boolean found = false;
        for (int i = transactions.size() - 1; i >= 0; i--){
            Transaction t = transactions.get(i);
            try {
                // Parse the transaction date string safely into a LocalDate
                LocalDate tDate = LocalDate.parse(t.getDate().trim());

                // Compare by year and month integers so formatting variations don't matter
                if (tDate.getYear() == targetYear && tDate.getMonthValue() == targetMonth){
                    String formattedAmount = String.format("%.2f", t.getAmount());

                    System.out.println(cyan + "Date: " + reset + t.getDate());
                    System.out.println(cyan + " | Time: " + reset + t.getTime());
                    System.out.println(cyan + " | Description: " + reset + t.getDescription());
                    System.out.println(cyan + " | Vendor: " + reset + t.getVendor());
                    System.out.println(cyan + " | Amount: " + reset + formattedAmount);
                    found = true;
                }
            } catch (Exception e) {
                System.out.println(yellow + "⚠️ " + red + "Could not parse date for row: " + t.getDate() + reset);            }
        }
        if (!found){
            System.out.println(red + "No transactions found for the previous month." + reset);
        }
    }

    // Method for year to date
    public static void displayYearToDate(Scanner scanner, ArrayList<Transaction> transactions){
        String pink = "\u001B[95m";
        String cyan = "\u001B[36m";
        String red = "\u001B[31m";
        String reset = "\u001B[0m";

        String currentYear = String.valueOf(LocalDate.now().getYear());

        System.out.println(pink + "\n╔═══════════════════════════════════╗");
        System.out.println("║        " + cyan + "YEAR TO DATE REPORTS" + pink + "       ║");
        System.out.println("╠═══════════════════════════════════╣");

        boolean found = false;
        for (int i = transactions.size() - 1; i >= 0; i--){
            Transaction t = transactions.get(i);
            if (t.getDate().startsWith(currentYear)){
                String formattedAmount = String.format("%.2f", t.getAmount());

                System.out.println(cyan + "Date: " + reset + t.getDate());
                System.out.println(cyan + " | Time: " + reset + t.getTime());
                System.out.println(cyan + " | Description: " + reset + t.getDescription());
                System.out.println(cyan + " | Vendor: " + reset + t.getVendor());
                System.out.println(cyan + " | Amount: " + reset + formattedAmount);
                found = true;
            }
        }
        if (!found){
            System.out.println(red + "No transactions found for this year." + reset);
        }
    }

    // Method for previous year
    public static void displayPreviousYear(Scanner scanner, ArrayList<Transaction> transactions){
        String pink = "\u001B[95m";
        String cyan = "\u001B[36m";
        String yellow = "\u001B[33m";
        String red = "\u001B[31m";
        String reset = "\u001B[0m";

        // Get the target year number for last year
        int targetYear = LocalDate.now().getYear() - 1;

        System.out.println(pink + "\n╔═══════════════════════════════════╗");
        System.out.println("║        " + cyan + "PREVIOUS YEAR REPORTS" + pink + "      ║");
        System.out.println("╠═══════════════════════════════════╣");

        boolean found = false;
        for (int i = transactions.size() - 1; i >= 0; i--){
            Transaction t = transactions.get(i);
            try {
                LocalDate tDate = LocalDate.parse(t.getDate().trim());

                // Compare by year integer
                if (tDate.getYear() == targetYear){
                    String formattedAmount = String.format("%.2f", t.getAmount());

                    System.out.println(cyan + "Date: " + reset + t.getDate());
                    System.out.println(cyan + " | Time: " + reset + t.getTime());
                    System.out.println(cyan + " | Description: " + reset + t.getDescription());
                    System.out.println(cyan + " | Vendor: " + reset + t.getVendor());
                    System.out.println(cyan + " | Amount: " + reset + formattedAmount);
                    found = true;
                }
            } catch (Exception e) {
                System.out.println(yellow + "⚠️ " + red + "Could not parse date for row: " + t.getDate() + reset);
            }
        }
        if (!found){
            System.out.println(red + "No transactions found for the previous year." + reset);
        }
    }
}