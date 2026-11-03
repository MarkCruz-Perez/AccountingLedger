package com.pluralsight;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private BigDecimal amount;

    public Transaction(LocalDate date, LocalTime time,
                       String description, String vendor,
                       BigDecimal amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
    public String getDescription() {
        return description;
    }
    public String getVendor() {
        return vendor;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    //Convert Transaction to CSV
    public String toCsv() {
        return date + "|" + time + "|" + description + "|" + vendor + "|" +
                amount.setScale(2).toPlainString();
    }
    @Override
    public String toString() {
    return String.format(
   "%-12s %-12s %-25s %-20s %12s",
            date,
            time,
            description,
            vendor,
            amount.setScale(2).toPlainString()
    );

    }
}
