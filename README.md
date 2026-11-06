# 💰 Accounting Ledger Application

A colorful Java command-line application for tracking business or personal financial transactions, with ledger views, pre-defined reports, and vendor search, all saved to a simple CSV file.

![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk&logoColor=white)
![Interface](https://img.shields.io/badge/Interface-CLI-blue)
![Storage](https://img.shields.io/badge/Storage-CSV-green)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen)

> **Capstone 1** project built as part of a Java development academy.

---

## 📖 Table of Contents

1. [Overview](#-overview)
2. [Features](#-features)
3. [User Stories](#-user-stories)
4. [Application Screens and Navigation](#-application-screens-and-navigation)
5. [Technologies Used](#-technologies-used)
6. [File Structure](#-file-structure)
7. [How to Run the Application](#-how-to-run-the-application)
8. [CSV Data Format Example](#-csv-data-format-example)
9. [Example Usage](#-example-usage)
10. [Interesting Code Highlight](#-interesting-code-highlight)
11. [How the Project Developed](#-how-the-project-developed)
12. [Future Enhancements](#-future-enhancements)
13. [Author](#-author)

---

## 🔎 Overview

The **Accounting Ledger Application** lets users record and review financial activity for a business or a personal account, all from the terminal. Every transaction is loaded from and saved to a plain-text file named `transactions.csv`, so data persists between sessions and is easy to inspect or back up.

Each transaction contains:

| Field         | Description                                          |
|---------------|------------------------------------------------------|
| `date`        | Date of the transaction (`yyyy-MM-dd`)               |
| `time`        | Time of the transaction (`HH:mm:ss`)                 |
| `description` | What the transaction was for                         |
| `vendor`      | Who the money was paid to or received from           |
| `amount`      | Positive for deposits, negative for payments         |

The app keeps running until the user chooses to exit, and the ledger views show the **newest entries first**. The menus use color-coded, box-drawn screens for a polished terminal experience.

---

## ✨ Features

- **Add Deposits**: record money coming in; the date and time are generated automatically
- **Make Payments (Debits)**: record money going out; amounts are automatically saved as negative values
- **Ledger View**
  - Display all transactions
  - Filter deposits only
  - Filter payments only
- **Reports Menu**
  - Month To Date
  - Previous Month
  - Year To Date
  - Previous Year
  - Search by Vendor (case-insensitive)
- **Persistent storage**: transactions are loaded from `transactions.csv` at startup and appended on every new entry
- **Newest-first views** for the ledger (All, Deposits, Payments) and the Previous Month, Year To Date, and Previous Year reports
- **Consistent money formatting**: amounts always display with two decimal places
- **Safe date handling**: Previous Month and Previous Year reports parse dates and warn about any row that can't be read
- **Colorful CLI design** with ANSI colors and box-drawing menus
- **Friendly messages** when no matching transactions are found

---

## 📋 User Stories

1. **As a user**, I want to add a deposit so that I can record money received.
2. **As a user**, I want to make a payment so that I can record money I spent.
3. **As a user**, I want my transactions saved to a file so that my data is still there the next time I open the app.
4. **As a user**, I want to view all transactions, newest first, so that I can see my latest activity at a glance.
5. **As a user**, I want to view only deposits so that I can review my income.
6. **As a user**, I want to view only payments so that I can review my expenses.
7. **As a user**, I want month-to-date and year-to-date reports so that I can track my current income and spending.
8. **As a user**, I want previous-month and previous-year reports so that I can compare past periods.
9. **As a user**, I want to search by vendor so that I can see everything tied to a specific business or person.
10. **As a user**, I want to navigate between screens easily so that I can move around the app without restarting it.

---

## 🧭 Application Screens and Navigation

### 🏠 Home Screen

| Option | Action                                                        |
|--------|---------------------------------------------------------------|
| `D`    | **Add Deposit**: prompts for details and saves to the CSV     |
| `P`    | **Make Payment (Debit)**: prompts for details and saves to the CSV |
| `L`    | **Ledger**: opens the Ledger screen                           |
| `X`    | **Exit**: closes the application                              |

### 📒 Ledger Screen

| Option | Action                                              |
|--------|-----------------------------------------------------|
| `A`    | **All Transactions**: display every entry           |
| `D`    | **Deposits Only**: display positive entries         |
| `P`    | **Payments Only**: display negative entries         |
| `R`    | **Reports**: open the Reports screen                |
| `H`    | **Home**: return to the Home screen                 |

### 📊 Reports Screen

| Option | Action                                              |
|--------|-----------------------------------------------------|
| `1`    | **Month To Date**                                   |
| `2`    | **Previous Month**                                  |
| `3`    | **Year To Date**                                    |
| `4`    | **Previous Year**                                   |
| `5`    | **Search by Vendor**: prompts for a vendor name     |
| `0`    | **Back**: return to the Ledger screen               |

### Navigation Flow

```text
                 ┌──────────────┐
                 │  Home Screen │◄─────────────┐
                 └──────┬───────┘              │
        ┌───────┬───────┼───────┐              │
        ▼       ▼       ▼       ▼              │
   Add Deposit  Make   Ledger   Exit           │
        │     Payment    │                     │
        └──────┬─────────┤                     │
               │         │                     │
        (returns to Home)│                     │
                         ▼                     │
                 ┌──────────────┐              │
                 │    Ledger    ├──── H ───────┘
                 └──────┬───────┘
          ┌─────┬───────┼────────┐
          ▼     ▼       ▼        ▼
         All  Deposits Payments Reports
                                   │
                      MTD · Previous Month · YTD
                      Previous Year · Vendor Search
                                   │
                                   └── 0 (Back) ──► Ledger
```

---

## 🛠 Technologies Used

- **Java**: core language (JDK 17 or later recommended)
- **Java I/O**: `BufferedReader` / `FileReader` and `BufferedWriter` / `FileWriter` for reading and appending to the CSV
- **Java Time API**: `LocalDate`, `LocalTime`, and `DateTimeFormatter` for timestamps and date-based reports
- **Java Collections**: `ArrayList<Transaction>` to hold transactions in memory
- **`Scanner`** for user input
- **ANSI escape codes** for colored terminal output
- **Git & GitHub** for version control and project management
- **IntelliJ IDEA** *(or any Java IDE / terminal)*

---

## 📁 File Structure

```text
capstone-1/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── pluralsight/
│                   ├── Main.java          # Entry point, menus, ledger, reports, CSV read/write
│                   └── Transaction.java   # Transaction model (date, time, description, vendor, amount)
├── transactions.csv                       # Transaction data storage
├── .gitignore
└── README.md
```

---

## 🚀 How to Run the Application

### Prerequisites

- [Java JDK 17+](https://adoptium.net/) installed
- Git installed
- *(Optional)* IntelliJ IDEA or another Java IDE
- A terminal that supports ANSI colors (most modern terminals do, including IntelliJ's)

Verify Java is installed:

```bash
java -version
```

### Option 1: Run with an IDE (IntelliJ IDEA)

1. Clone the repository:
   ```bash
   git clone https://github.com/apega-tech/capstone-1.git
   ```
2. Open the project folder in IntelliJ IDEA.
3. Make sure `transactions.csv` is in the project root directory.
4. Open `Main.java` and click the green **Run** button.

### Option 2: Run from the Terminal

```bash
# 1. Clone the repository
git clone https://github.com/apega-tech/capstone-1.git
cd capstone-1

# 2. Compile
javac -d out src/main/java/com/pluralsight/*.java

# 3. Run (from the project root so transactions.csv is found)
java -cp out com.pluralsight.Main
```

> 📂 **Sample data:** `transactions.csv` is included in the project root with sample transactions, so there's something to look at right away.
>
> ▶️ **Main class:** the `main` method is in `com.pluralsight.Main` (`Main.java`).

> 💡 **Tip:** Run the application from the project root directory so it can find `transactions.csv`. If the file is missing, the app prints an error, starts with an empty ledger, and creates the file the first time you save a transaction.

### First Use

When the app starts, the pink **HOME SCREEN** menu appears. A good first tour:

1. Type `L` and press Enter to open the Ledger, then `A` to see all transactions, newest first.
2. Type `H` to go back Home, then `D` to add a deposit. Enter a description, a vendor, and an amount.
3. Open the Ledger again with `L`, then `R` for Reports. Try `3` (Year To Date) or `5` (Search by Vendor).
4. Type `0` to leave Reports, `H` to go Home, and `X` to exit.

---

## 🗂 CSV Data Format Example

Transactions are stored in `transactions.csv`, one per line, using the pipe (`|`) character as a delimiter:

```text
date|time|description|vendor|amount
```

**Example:**

```csv
date|time|description|vendor|amount
2025-06-15|14:00:00|previous year payment|Amazon|-120.00
2025-08-20|16:30:00|previous year deposit|Joe|1000.00
2026-01-10|09:00:00|january deposit this year|Joe|2000.00
2026-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
2026-04-18|11:15:00|Invoice 1001 paid|Joe|1500.00
2026-05-02|10:00:00|current month deposit|Joe|1500.00
2026-05-04|12:30:00|current month payment|Amazon|-50.00
2026-10-12|14:00:00|test october payment|Target|-35.00
```

| Amount Sign    | Meaning              |
|----------------|----------------------|
| Positive (`+`) | Deposit (money in)   |
| Negative (`-`) | Payment (money out)  |

---

## 💻 Example Usage

### 🎨 Color Scheme

The app uses ANSI color codes to make each screen easy to read:

| Color | Used for |
|-------|----------|
| 🟪 **Pink** | Menu borders and box frames |
| 🟦 **Cyan** | Titles, menu letters and numbers, prompts, and field labels |
| 🟩 **Green** | Success messages (deposit or payment saved) |
| 🟥 **Red** | Errors and "no transactions found" messages |
| 🟨 **Yellow** | Warnings (invalid menu choice) and the exit message |


### Home Screen

```text
╔═══════════════════════════════════╗
║            HOME SCREEN            ║
╠═══════════════════════════════════╣
║  D) Add Deposit                   ║
║  P) Make Payment (Debit)          ║
║  L) Ledger                        ║
║  X) Exit                          ║
╚═══════════════════════════════════╝
Choose an option: D
```

### Adding a Deposit

```text
╔═════════════════════════════════════╗
║            ADD A DEPOSIT            ║
╠═════════════════════════════════════╣
Description: Freelance web design project
Vendor: Bright Studios
Amount:
750.00
✅ Deposit successfully added and saved!
```

### Making a Payment

```text
╔═══════════════════════════════════╗
║          MAKE A PAYMENT           ║
╠═══════════════════════════════════╣
Description: Office chair
Vendor: IKEA
Amount:
129.99
✅ Payment successfully processed and saved!
```

### Viewing the Ledger (Newest First)

```text
╔═══════════════════════════════════╗
║            LEDGER MENU            ║
╠═══════════════════════════════════╣
║  A) All Transactions              ║
║  D) Deposits Only                 ║
║  P) Payments Only                 ║
║  R) Reports                       ║
║  H) Home (Main Menu)              ║
╚═══════════════════════════════════╝
Choose a Ledger option: A

╔═══════════════════════════════════╗
║         ALL TRANSACTIONS          ║
╠═══════════════════════════════════╣
Date: 2026-10-12
 | Time: 14:00:00
 | Description: test october payment
 | Vendor: Target
 | Amount: -35.00
Date: 2026-05-04
 | Time: 12:30:00
 | Description: current month payment
 | Vendor: Amazon
 | Amount: -50.00
Date: 2026-05-02
 | Time: 10:00:00
 | Description: current month deposit
 | Vendor: Joe
 | Amount: 1500.00
```

### Reports Menu

```text
╔═══════════════════════════════════╗
║            REPORTS MENU           ║
╠═══════════════════════════════════╣
║  1) Month To Date                 ║
║  2) Previous Month                ║
║  3) Year To Date                  ║
║  4) Previous Year                 ║
║  5) Search by Vendor              ║
║  0) Back (To Ledger Menu)         ║
╚═══════════════════════════════════╝
Choose a report option: 5
Enter vendor name to search: target

╔═══════════════════════════════════╗
║ TRANSACTIONS FOR VENDOR: target   ║
╠═══════════════════════════════════╣
Date: 2026-10-12
 | Time: 14:00:00
 | Description: test october payment
 | Vendor: Target
 | Amount: -35.00
```

---

## 🔍 Interesting Code Highlight

### Previous Month report: date logic that handles year changes

The **Previous Month** report needs to find last month's transactions, even when the current month is January and "last month" is December of the previous year. Instead of comparing text, the code works out the target year and month with `LocalDate`, then parses each transaction's date and compares real year and month numbers.

```java
// Get the target year and month numbers for last month
LocalDate prevMonth = LocalDate.now().minusMonths(1);
int targetYear = prevMonth.getYear();
int targetMonth = prevMonth.getMonthValue();

boolean found = false;
for (int i = transactions.size() - 1; i >= 0; i--){
    Transaction t = transactions.get(i);
    try {
        // Parse the transaction date string safely into a LocalDate
        LocalDate tDate = LocalDate.parse(t.getDate().trim());

        // Compare by year and month integers so formatting variations don't matter
        if (tDate.getYear() == targetYear && tDate.getMonthValue() == targetMonth){
            // ...print the transaction
            found = true;
        }
    } catch (Exception e) {
        // Warn about any row whose date can't be read
    }
}
```

### Why I'm proud of it

- `minusMonths(1)` handles the January-to-December rollover automatically, so I didn't need special-case code for the start of the year.
- Comparing year and month as **integers** after parsing is more reliable than comparing date strings.
- The loop runs from the end of the list to the start, so the newest transactions appear first.
- The `try/catch` keeps one badly formatted row from crashing the whole report, and it tells the user which row had the problem.

---

## 🛤 How the Project Developed

- **Core first:** I started with the home screen, adding deposits and payments, saving to `transactions.csv`, and the ledger views (All, Deposits, Payments).
- **Newest first:** Transactions are stored in an `ArrayList` in the order they were added, so the ledger views loop from the end of the list to the start to show the newest entries first.
- **Reports and dates:** Month To Date and Year To Date match on the start of the date text (like `2026-05`). For Previous Month and Previous Year I later switched to parsing each date into a `LocalDate` and comparing real year and month numbers, which is more reliable and lets the app warn about a bad row instead of crashing.
- **Consistent amounts:** I added `String.format("%.2f", ...)` to every view so amounts always show two decimals (`1500.00` instead of `1500.0`).
- **Customizing the app (bonus):** I added box-drawn menus and ANSI colors to give the CLI its own look.
- **Not built yet:** The Custom Search bonus isn't implemented, so it is listed under Future Enhancements.

### 📌 Project Tracking

- [GitHub Project board](LINK-TO-YOUR-PROJECT-BOARD)

---

## 🔮 Future Enhancements

- 🔎 **Custom Search** (bonus feature) to filter by start date, end date, description, vendor, and amount, applying only the filters the user fills in
- 🛡 Add **input validation** and error handling (invalid menu entries, non-numeric amounts, invalid dates)
- 🔤 Support **partial vendor matching** (for example, searching `Ama` to find `Amazon`)
- 📈 Show a running **account balance** and totals for deposits and payments
- 🧱 **Refactor** into separate `Ledger` and `Reports` classes and reuse a single print method to reduce repeated code
- 🏷 Add **transaction categories** (Food, Rent, Software) and category reports
- 📤 **Export** reports to separate CSV or PDF files
- ✏️ Add the ability to **edit or delete** transactions
- 🧪 Add **unit tests** with JUnit
- 🖥 Build a **GUI or web version** using JavaFX or Spring Boot

---

## 👤 Author

**Anastacia Pena**

- 💼 LinkedIn: [linkedin.com/in/apega-tech](https://www.linkedin.com/in/apega-tech)
- 🐙 GitHub: [@apega-tech](https://github.com/apega-tech)
- 📧 Email: [anastaciapena23@gmail.com](mailto:anastaciapena23@gmail.com)
- 🌐 Portfolio: [apega-tech.github.io](https://apega-tech.github.io/)

---

⭐ *If you found this project helpful or interesting, feel free to give it a star!*
