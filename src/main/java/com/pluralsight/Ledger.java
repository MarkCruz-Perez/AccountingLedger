package com.pluralsight;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class Ledger {

    private final Path file = Path.of("transactions.csv");

    private final List<Transaction> transactions = new ArrayList<>();

    //Load transactions from CSV
    public Ledger() {
        loadTransactions();
    }

    private void loadTransactions() {

        try {
            if (!Files.exists(file)) {
                Files.createFile(file);
            }
            List<String> lines = Files.readAllLines(file);

            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }


                String[] parts = line.split("\\|", -1);

                if (parts.length != 5) {

                    System.out.println("Invalid transaction skipped: " + line);
                    continue;

                }

                try {

                    Transaction transaction = new Transaction(
                            LocalDate.parse(parts[0]),
                            LocalTime.parse(parts[1]),
                            parts[2],
                            parts[3],
                            new BigDecimal(parts[4])
                    );

                    transactions.add(transaction);

                }catch (RuntimeException e) {
                    System.out.println("Invalid transaction skipped: " + line);

                    System.out.println("Reason: " + e.getMessage());

                }
            }

        } catch (IOException e) {

            System.out.println("Error reading transactions: " + e.getMessage());

        }

    }
    private String clean(String value) {

        return value.replace("|", "/")
                .replace("\n", " ")
                .replace("\r", " ");
    }

    // Add and save a transaction.
    public void addTransaction(Transaction transaction) {

        Transaction safeTransaction = new Transaction(
                transaction.getDate(),
                transaction.getTime(),
                clean(transaction.getDescription()),
                clean(transaction.getVendor()),
                transaction.getAmount()
        );

        try {

            Files.writeString(
                    file,
                    safeTransaction.toCsv() + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

            transactions.add(safeTransaction);

            System.out.println(
                    "Transaction saved successfully!"
            );

        } catch (IOException e) {

            System.out.println(
                    "Error saving transaction: " + e.getMessage()
            );
        }
    }

    // Return transactions from newest to oldest.
    public List<Transaction> getNewestFirst() {

        return transactions.stream()
                .sorted(
                        Comparator.comparing(Transaction::getDate)
                                .thenComparing(Transaction::getTime)
                                .reversed()
                )
                .toList();
    }

    // Display a list of transactions.
    public void displayTransactions(List<Transaction> list) {

        if (list.isEmpty()) {

            System.out.println("No transactions found.");
            return;
        }

        System.out.printf(
                "%-12s %-12s %-25s %-20s %12s%n",
                "DATE",
                "TIME",
                "DESCRIPTION",
                "VENDOR",
                "AMOUNT"
        );

        System.out.println(
                "--------------------------------------------------------------------------------"
        );

        for (Transaction transaction : list) {
            System.out.println(transaction);
        }
    }

    // Display all transactions.
    public void displayAll() {

        displayTransactions(getNewestFirst());
    }

    // Display deposits only.
    public void displayDeposits() {

        List<Transaction> results = getNewestFirst()
                .stream()
                .filter(t -> t.getAmount().signum() > 0)
                .toList();

        displayTransactions(results);
    }

    // Display payments only.
    public void displayPayments() {

        List<Transaction> results = getNewestFirst()
                .stream()
                .filter(t -> t.getAmount().signum() < 0)
                .toList();

        displayTransactions(results);
    }

    // Reusable filtering method.
    public void displayFiltered(Predicate<Transaction> condition) {

        List<Transaction> results = getNewestFirst()
                .stream()
                .filter(condition)
                .toList();

        displayTransactions(results);
    }

    // Month to date report.
    public void monthToDate() {

        LocalDate today = LocalDate.now();
        LocalDate start = today.withDayOfMonth(1);

        displayFiltered(t ->
                !t.getDate().isBefore(start) &&
                        !t.getDate().isAfter(today)
        );
    }

    // Previous month report.
    public void previousMonth() {

        YearMonth previousMonth =
                YearMonth.now().minusMonths(1);

        displayFiltered(t ->
                YearMonth.from(t.getDate()).equals(previousMonth)
        );
    }

    // Year to date report.
    public void yearToDate() {

        LocalDate today = LocalDate.now();
        LocalDate start = today.withDayOfYear(1);

        displayFiltered(t ->
                !t.getDate().isBefore(start) &&
                        !t.getDate().isAfter(today)
        );
    }

    // Previous year report.
    public void previousYear() {

        int previousYear = LocalDate.now().getYear() - 1;

        displayFiltered(t ->
                t.getDate().getYear() == previousYear
        );
    }

    // Search transactions by vendor.
    public void searchVendor(String vendor) {

        displayFiltered(t ->
                t.getVendor().equalsIgnoreCase(vendor)
        );
    }

    // Custom search with optional filters.
    public void customSearch(
            String startDate,
            String endDate,
            String description,
            String vendor,
            String amount
    ) {

        LocalDate start = startDate.isBlank()
                ? null : LocalDate.parse(startDate);

        LocalDate end = endDate.isBlank()
                ? null : LocalDate.parse(endDate);

        BigDecimal targetAmount = amount.isBlank()
                ? null : new BigDecimal(amount).setScale(
                2,
                java.math.RoundingMode.UNNECESSARY
        );

        // Validate date range.
        if (start != null && end != null &&
                start.isAfter(end)) {

            System.out.println(
                    "Error: Start date cannot be after end date."
            );

            return;
        }

        // Filter transactions.
        displayFiltered(t -> {

            boolean matches = true;

            // Start date filter.
            if (start != null) {
                matches = matches &&
                        !t.getDate().isBefore(start);
            }

            // End date filter.
            if (end != null) {
                matches = matches &&
                        !t.getDate().isAfter(end);
            }

            // Description filter.
            if (!description.isBlank()) {
                matches = matches &&
                        t.getDescription()
                                .toLowerCase()
                                .contains(description.toLowerCase());
            }

            // Vendor filter.
            if (!vendor.isBlank()) {
                matches = matches &&
                        t.getVendor().equalsIgnoreCase(vendor);
            }

            // Amount filter.
            if (targetAmount != null) {
                matches = matches &&
                        t.getAmount().compareTo(targetAmount) == 0;
            }

            return matches;
        });
    }
}



