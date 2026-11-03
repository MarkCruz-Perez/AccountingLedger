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

    }
}
