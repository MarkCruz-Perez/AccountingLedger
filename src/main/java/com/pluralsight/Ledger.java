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

        } catch (IOException e) {}
    }

}
