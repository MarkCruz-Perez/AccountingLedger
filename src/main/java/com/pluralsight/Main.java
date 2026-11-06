package com.pluralsight;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

 public class Main {
     //Scanner reads information entered by user
     //I put it in static so all methods in Main can use it
     private static final Scanner scanner =
            new Scanner(System.in);

    private static final Ledger ledger = new Ledger();

    // Main method: application starts here.
    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println("       MY ACCOUNTING LEDGER");
        System.out.println("==================================");

        //Controls whether the application continues running
        boolean running = true;

        while (running) {

            System.out.println("\n========== HOME SCREEN ==========");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            System.out.print("\nChoose an option: ");

            String choice = scanner.nextLine()
                    .trim()
                    .toUpperCase();

            //Determines actions to preform
            switch (choice) {

                case "D":
                    addDeposit();
                    break;

                case "P":
                    makePayment();
                    break;

                case "L":
                    ledgerMenu();
                    break;

                case "X":
                    running = false;
                    System.out.println(
                            "Thank you for using Accounting Ledger!"
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please try again."
                    );
            }
        }

        scanner.close();
    }

    // Add a deposit.
    private static void addDeposit() {

        System.out.println("\n========== ADD DEPOSIT ==========");

        String description = getInput("Description: ");
        String vendor = getInput("Vendor: ");

        BigDecimal amount = getPositiveAmount();

        Transaction transaction = new Transaction(
                LocalDate.now(),
                LocalTime.now().withNano(0),
                description,
                vendor,
                amount
        );

        ledger.addTransaction(transaction);
    }

    // Make a payment.
    private static void makePayment() {

        System.out.println("\n========== MAKE PAYMENT ==========");

        String description = getInput("Description: ");
        String vendor = getInput("Vendor: ");

        BigDecimal amount = getPositiveAmount();

        // Convert payment to a negative amount.
        amount = amount.negate();

        Transaction transaction = new Transaction(
                LocalDate.now(),
                LocalTime.now().withNano(0),
                description,
                vendor,
                amount
        );

        ledger.addTransaction(transaction);
    }

    // Get a valid positive monetary amount.
    private static BigDecimal getPositiveAmount() {

        while (true) {

            try {

                System.out.print("Amount: $");

                BigDecimal amount = new BigDecimal(
                        scanner.nextLine().trim()
                );

                // Amount must be greater than zero.
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {

                    System.out.println(
                            "Amount must be greater than zero."
                    );

                    continue;
                }

                // Allow only two decimal places.
                amount = amount.setScale(
                        2,
                        java.math.RoundingMode.UNNECESSARY
                );

                return amount;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid monetary amount."
                );

            } catch (ArithmeticException e) {

                System.out.println(
                        "Please enter no more than two decimal places."
                );
            }
        }
    }

    // Get non-empty text input.
    private static String getInput(String prompt) {

        while (true) {

            System.out.print(prompt);

            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println(
                    "This field cannot be empty."
            );
        }
    }

    // Display ledger menu.
    private static void ledgerMenu() {

        boolean inLedger = true;

        while (inLedger) {

            System.out.println("\n========== LEDGER ==========");
            System.out.println("A) All Transactions");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            System.out.print("\nChoose an option: ");

            String choice = scanner.nextLine()
                    .trim()
                    .toUpperCase();

            switch (choice) {

                case "A":
                    ledger.displayAll();
                    break;

                case "D":
                    ledger.displayDeposits();
                    break;

                case "P":
                    ledger.displayPayments();
                    break;

                case "R":
                    reportsMenu();
                    break;

                case "H":
                    inLedger = false;
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please try again."
                    );
            }
        }
    }

    // Display reports menu.
    private static void reportsMenu() {

        boolean inReports = true;

        while (inReports) {

            System.out.println("\n========== REPORTS ==========");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search By Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back");

            System.out.print("\nChoose an option: ");

            String choice = scanner.nextLine().trim();

            try {

                switch (choice) {

                    case "1":
                        ledger.monthToDate();
                        break;

                    case "2":
                        ledger.previousMonth();
                        break;

                    case "3":
                        ledger.yearToDate();
                        break;

                    case "4":
                        ledger.previousYear();
                        break;

                    case "5":

                        String vendor = getInput(
                                "Enter vendor name: "
                        );

                        ledger.searchVendor(vendor);
                        break;

                    case "6":
                        customSearchMenu();
                        break;

                    case "0":
                        inReports = false;
                        break;

                    default:
                        System.out.println(
                                "Invalid option. Please try again."
                        );
                }

            } catch (RuntimeException e) {

                System.out.println(
                        "Invalid search information: " + e.getMessage()
                );

                System.out.println(
                        "Please check your dates and amount."
                );
            }
        }
    }

    // Custom search menu.
    private static void customSearchMenu() {

        System.out.println("\n========== CUSTOM SEARCH ==========");

        System.out.println(
                "Leave a field blank to ignore that filter."
        );

        System.out.print("Start Date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine().trim();

        System.out.print("End Date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine().trim();

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine().trim();

        System.out.print("Amount: ");
        String amount = scanner.nextLine().trim();

        ledger.customSearch(
                startDate,
                endDate,
                description,
                vendor,
                amount
        );
    }
}


