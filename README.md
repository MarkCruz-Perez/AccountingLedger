Java Accounting Ledger Application
1. Project Overview
   The Accounting Ledger Application is a Java-based command-line program designed to help users keep track of their personal or business financial transactions. The program allows users to record deposits, record payments, view their financial history, and generate reports based on transaction dates, vendors, descriptions, and amounts.
   The purpose of this project is to create a simple accounting system that organizes financial transactions and stores them in a file so that information is not lost when the application closes.
   The application uses Java classes, object-oriented programming, file handling, user input, and financial calculations to manage transactions.
   All transactions are saved in a file named transactions.csv.
2. Main Features
   The Accounting Ledger includes the following features:
   Record Deposits: Add money received into the account.
   Record Payments: Record money spent or paid out.
   View All Transactions: Display every transaction saved in the ledger.
   View Deposits: Display only transactions with positive amounts.
   View Payments: Display only transactions with negative amounts.
   Month-to-Date Report: Display transactions from the beginning of the current month through today.
   Previous Month Report: Display transactions from the previous calendar month.
   Year-to-Date Report: Display transactions from the beginning of the current year through today.
   Previous Year Report: Display transactions from the previous calendar year.
   Search by Vendor: Find transactions associated with a particular vendor.
   Custom Search: Search using a date range, description, vendor, or amount.
   Save Transactions: Store financial records in a CSV file so they can be loaded again later.
3. How to Use the Accounting Ledger
   When the program starts, the user is presented with the home screen.
   Home Screen
   The home screen provides the main options for managing transactions.
   HOME SCREEN

D) Add Deposit
P) Make Payment
L) Ledger
X) Exit
D - Add Deposit
Select D when money is being added to the account.
The program asks the user to enter:
Description of the deposit
Vendor or source of the deposit
Deposit amount
The application automatically records the current date and time. The deposit is saved as a positive amount.
Example:
Description: Paycheck
Vendor: ABC Company
Amount: 1500.00
The transaction is saved to the ledger as a deposit of $1,500.00.
P - Make Payment
Select P when recording money being spent or paid out.
The program asks the user to enter:
Description of the payment
Vendor receiving the payment
Payment amount
The application automatically records the current date and time. Payments are saved as negative amounts so they can be distinguished from deposits.
Example:
Description: Grocery shopping
Vendor: Grocery Store
Amount: 85.50
The payment is stored as -85.50 in the ledger.
L - Ledger
Select L to open the ledger menu. This menu allows users to view transactions and access financial reports.
X - Exit
Select X to close the application.
Transactions that have already been saved in transactions.csv remain available the next time the program is started.
4. How to Use the Ledger Menu
   After selecting L from the home screen, the ledger menu appears.
   LEDGER MENU

A) All Transactions
D) Deposits
P) Payments
R) Reports
H) Home
A - All Transactions
Displays all transactions saved in the ledger, including deposits and payments.
Transactions are displayed with the newest transactions first.
D - Deposits
Displays only deposits. Deposits are identified by positive amounts.
P - Payments
Displays only payments. Payments are identified by negative amounts.
R - Reports
Opens the reports menu, where users can view transactions within specific time periods or search for transactions.
H - Home
Returns the user to the main home screen.
5. How to Use the Reports Menu
   The reports menu allows users to organize and search financial transactions.
   REPORTS MENU

1) Month to Date
2) Previous Month
3) Year to Date
4) Previous Year
5) Search by Vendor
6) Custom Search
0) Back
   1 - Month to Date
   Displays transactions beginning on the first day of the current month and ending on the current date.
   2 - Previous Month
   Displays transactions from the entire previous calendar month.
   3 - Year to Date
   Displays transactions beginning January 1 of the current year through the current date.
   4 - Previous Year
   Displays transactions from the previous calendar year.
   5 - Search by Vendor
   Allows the user to enter a vendor name and search for transactions associated with that vendor.
   For example, searching for Walmart displays matching transactions saved under that vendor.
   6 - Custom Search
   Allows users to search for transactions using multiple filters.
   The available search fields are:
   Start date
   End date
   Description
   Vendor
   Amount
   Users can leave a field blank if they do not want to filter by that field.
   For example, a user could enter a start date and end date to find transactions within a particular period. They could also enter a vendor name to narrow the results.
   0 - Back
   Returns the user to the previous menu.
6. How the Application Stores Transactions
   The application uses a file named transactions.csv to store transaction information.
   Each transaction is saved using five fields separated by vertical bars:
   date|time|description|vendor|amount
   Example:
   2026-11-05|14:30:00|Paycheck|ABC Company|1500.00
   2026-11-05|15:00:00|Groceries|Walmart|-85.50
   Each field represents:
   Field
   Description
   Date
   Date the transaction occurred
   Time
   Time the transaction occurred
   Description
   Explanation of the transaction
   Vendor
   Business or source associated with the transaction
   Amount
   Financial value of the transaction

Deposits use positive amounts, and payments use negative amounts.
When the application starts, it reads the existing CSV file and loads the transactions into memory. When a new transaction is recorded, it is appended to the file.
This allows the user to close the program and return later without losing previously recorded transactions.
7. Detailed Explanation of the Java Code
   The application is divided into three main Java classes:
   Main.java
   Ledger.java
   Transaction.java
   Each class has a specific responsibility. Separating the code into different classes makes the application easier to understand, maintain, and update.
   A. Main.java
   The Main.java class controls the application and communicates with the user.
   It is responsible for:
   Starting the program.
   Displaying the home screen.
   Reading user selections.
   Opening the ledger and reports menus.
   Asking the user for transaction information.
   Validating transaction amounts.
   Creating deposit and payment transactions.
   Sending new transactions to the Ledger class.
   Main Method
   The program starts with:
   public static void main(String[] args)
   This is the entry point of the Java application.
   When the program starts, it creates a Ledger object and begins displaying the home menu.
   Scanner
   The Scanner class is used to receive input from the user through the command line.
   For example, when the user selects D to add a deposit, Scanner reads the user's selection and allows the program to continue to the deposit-entry process.
   Home Menu
   The home menu uses a loop to keep the application running until the user chooses to exit.
   The menu checks the user's selection and determines which method should run.
   Add Deposit Method
   The deposit method collects the transaction description, vendor, and amount.
   It obtains the current date and time using Java's LocalDate and LocalTime classes.
   It then creates a new Transaction object and sends it to the Ledger class to be saved.
   Make Payment Method
   The payment method works similarly to the deposit method.
   The difference is that the entered payment amount is converted into a negative amount before the transaction is saved.
   For example, entering 50.00 results in a stored transaction amount of -50.00.
   Amount Validation
   The amount validation method checks that the user enters a valid positive number.
   It prevents users from entering zero or negative amounts when entering a deposit or payment.
   It also checks that the amount has no more than two decimal places.
   This helps prevent invalid financial values from being recorded.
   Ledger Menu and Reports Menu
   These methods display additional menus and respond to the user's selections.
   They call the appropriate methods in the Ledger class to display transactions or generate reports.
   B. Ledger.java
   The Ledger.java class handles transaction management and file storage.
   It is responsible for:
   Loading transactions from the CSV file.
   Saving new transactions.
   Keeping transactions organized.
   Displaying transactions.
   Sorting transactions by date and time.
   Filtering deposits and payments.
   Generating reports.
   Searching for transactions.
   Loading Transactions
   When the Ledger object is created, its constructor calls the method that loads existing transactions.
   The program checks whether transactions.csv exists. If the file does not exist, it creates the file.
   If the file already exists, the program reads its contents and converts each valid line into a Transaction object.
   Saving Transactions
   The addTransaction() method receives a transaction and writes it to transactions.csv.
   It uses Java file-handling classes such as Files and Path.
   The transaction is added to the file using append mode. This means new transactions are added without deleting existing records.
   Displaying Transactions
   The Ledger class includes methods that display:
   All transactions.
   Deposits only.
   Payments only.
   The program identifies deposits by checking whether the amount is greater than zero.
   It identifies payments by checking whether the amount is less than zero.
   Sorting Transactions
   The program sorts transactions by date and time in descending order.
   This means the newest transactions appear first when the ledger is displayed.
   Generating Reports
   The Ledger class uses Java date classes such as LocalDate and YearMonth to calculate date ranges.
   These calculations allow the application to identify transactions belonging to the current month, previous month, current year, or previous year.
   Vendor Search
   The vendor search compares the vendor entered by the user with the vendor information stored in each transaction.
   The search is case-insensitive, meaning uppercase and lowercase letters do not affect the match.
   Custom Search
   The custom search allows users to filter transactions using multiple conditions.
   The program can check:
   Whether a transaction falls within a selected date range.
   Whether its description contains the entered search text.
   Whether its vendor matches the entered vendor.
   Whether its amount matches the entered amount.
   Blank search fields are ignored, allowing users to search using only the information they have.
   C. Transaction.java
   The Transaction.java class represents one individual financial transaction.
   Instead of storing transaction information as unrelated pieces of data, the program groups the information together in a Transaction object.
   Each Transaction object contains five fields:
   Variable
   Java Data Type
   Purpose
   date
   LocalDate
   Stores the transaction date
   time
   LocalTime
   Stores the transaction time
   description
   String
   Stores the transaction description
   vendor
   String
   Stores the vendor or source
   amount
   BigDecimal
   Stores the transaction amount

Constructor
The constructor creates a new Transaction object and assigns values to its fields.
It also checks for missing required values and ensures the amount has exactly two decimal places without rounding an invalid value.
Getters
Getter methods allow other classes to retrieve transaction information.
For example, getDate() returns the transaction date, while getAmount() returns the transaction amount.
This allows the Ledger class to examine and filter transaction information.
BigDecimal
The application uses BigDecimal instead of double to handle financial amounts.
This is important because floating-point types such as double can produce small precision errors when performing decimal calculations.
BigDecimal provides more reliable decimal handling for financial records.
toCsv() Method
The toCsv() method converts a Transaction object into a line of text that can be saved in transactions.csv.
It combines the date, time, description, vendor, and amount using vertical bars as separators.
toString() Method
The toString() method formats the transaction information so it can be displayed in a readable format in the command-line application.
8. How the Three Classes Work Together
   The three classes work together to complete the accounting process.
   For example, when a user records a deposit:
   Main.java displays the home menu.
   The user selects D to add a deposit.
   Main.java asks for the description, vendor, and amount.
   The program validates the amount.
   Main.java creates a new Transaction object.
   The Transaction object stores the date, time, description, vendor, and amount.
   Main.java sends the Transaction object to Ledger.java.
   Ledger.java saves the transaction to transactions.csv.
   The transaction is available for viewing and searching.
   The same process is used when recording a payment, except the amount is saved as a negative value.
9. Project Structure
   The project files are organized as follows:
   AccountingLedger/
   │
   ├── src/
   │   ├── Main.java
   │   ├── Ledger.java
   │   └── Transaction.java
   │
   ├── transactions.csv
   │
   ├── out/
   │
   └── README.md

Main.java controls the menus and user interaction.
Ledger.java manages transactions, reports, and file storage.
Transaction.java defines the structure of an individual transaction.
transactions.csv stores the financial records.
out/ contains compiled Java class files.
README.md explains the project and how to use it.
The CSV file is created automatically if it does not already exist.
10. Requirements to Run the Project
    Before running the application, make sure the following are installed:
    Java Development Kit (JDK) 17 or later.
    A command-line terminal or Java-compatible IDE.
    Git, if cloning the project from GitHub.
11. How to Compile and Run the Java Project
    Step 1: Download or Clone the Project
    Clone the GitHub repository:
    git clone YOUR_GITHUB_REPOSITORY_URL
    Replace the placeholder with the actual URL of the GitHub repository.
    Step 2: Open the Project Folder
    Open the AccountingLedger folder in your terminal or Java IDE.
    Step 3: Compile the Java Files
    If your Java files are inside the src folder, run:
    javac -d out src/*.java
    This command compiles all Java files in the source folder and places the compiled class files in the out folder.
    Step 4: Start the Application
    Run the following command:
    java -cp out Main
    The application should start and display the home screen.
    Step 5: Begin Using the Ledger
    Choose an option from the home screen to record deposits, make payments, view the ledger, or generate reports.
12. Conclusion
    The Java Accounting Ledger Application demonstrates how Java can be used to create a basic financial record-keeping system.
    The project uses object-oriented programming by separating the application into three classes with different responsibilities. It also demonstrates user input, input validation, date and time handling, financial calculations, collections, sorting, searching, and file storage.
    By saving transactions in a CSV file, the application allows users to maintain financial records between program sessions.
    This project provides a foundation for understanding how a simple accounting application can be developed and expanded using Java.

