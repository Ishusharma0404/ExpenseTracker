import java.io.*;
import java.util.*;

class Expense {
    String name;
    double amount;
    String category;

    public Expense(String name, double amount, String category) {
        this.name = name;
        this.amount = amount;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Expense Name: " + name + ", Amount: $" + amount + ", Category: " + category;
    }
}

public class ExpenseTracker {
    private static List<Expense> expenses = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "expenses.txt";

    public static void main(String[] args) {
        loadExpensesFromFile();  // Load expenses from file at startup

        int choice;
        do {
            System.out.println("\n--- Expense Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Delete Expense");
            System.out.println("4. View Total Expenses");
            System.out.println("5. Edit Expense");
            System.out.println("6. Filter Expenses by Category");
            System.out.println("7. Sort Expenses by Amount");
            System.out.println("8. Save Expenses to File");
            System.out.println("9. Load Expenses from File");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    deleteExpense();
                    break;
                case 4:
                    viewTotalExpenses();
                    break;
                case 5:
                    editExpense();
                    break;
                case 6:
                    filterExpensesByCategory();
                    break;
                case 7:
                    sortExpensesByAmount();
                    break;
                case 8:
                    saveExpensesToFile();
                    break;
                case 9:
                    loadExpensesFromFile();
                    break;
                case 10:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 10);
    }

    private static void addExpense() {
        System.out.print("Enter expense name: ");
        String name = scanner.nextLine();
        System.out.print("Enter expense amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter expense category (e.g., Food, Travel, etc.): ");
        String category = scanner.nextLine();

        expenses.add(new Expense(name, amount, category));
        System.out.println("Expense added successfully!");
    }

    private static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses added yet.");
        } else {
            System.out.println("\n--- All Expenses ---");
            for (int i = 0; i < expenses.size(); i++) {
                System.out.println((i + 1) + ". " + expenses.get(i));
            }
        }
    }

    private static void deleteExpense() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses to delete.");
            return;
        }

        System.out.print("Enter the number of the expense to delete: ");
        int index = scanner.nextInt() - 1;

        if (index >= 0 && index < expenses.size()) {
            expenses.remove(index);
            System.out.println("Expense deleted successfully!");
        } else {
            System.out.println("Invalid expense number.");
        }
    }

    private static void viewTotalExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.amount;
        }
        System.out.println("Total expenses: $" + total);
    }

    private static void editExpense() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses to edit.");
            return;
        }

        System.out.print("Enter the number of the expense to edit: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();  // Consume newline

        if (index >= 0 && index < expenses.size()) {
            Expense expense = expenses.get(index);
            System.out.println("Current details: " + expense);

            System.out.print("Enter new name (leave blank to keep current): ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) expense.name = name;

            System.out.print("Enter new amount (enter 0 to keep current): ");
            double amount = scanner.nextDouble();
            if (amount != 0) expense.amount = amount;
            scanner.nextLine();  // Consume newline

            System.out.print("Enter new category (leave blank to keep current): ");
            String category = scanner.nextLine();
            if (!category.isEmpty()) expense.category = category;

            System.out.println("Expense updated successfully!");
        } else {
            System.out.println("Invalid expense number.");
        }
    }

    private static void filterExpensesByCategory() {
        System.out.print("Enter category to filter by: ");
        String category = scanner.nextLine();

        List<Expense> filtered = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.category.equalsIgnoreCase(category)) {
                filtered.add(expense);
            }
        }

        if (filtered.isEmpty()) {
            System.out.println("No expenses found in this category.");
        } else {
            System.out.println("\n--- Filtered Expenses ---");
            for (Expense expense : filtered) {
                System.out.println(expense);
            }
        }
    }

    private static void sortExpensesByAmount() {
        expenses.sort(Comparator.comparingDouble(exp -> exp.amount));
        System.out.println("Expenses sorted by amount.");
    }

    private static void saveExpensesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Expense expense : expenses) {
                writer.write(expense.name + "," + expense.amount + "," + expense.category);
                writer.newLine();
            }
            System.out.println("Expenses saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving expenses to file: " + e.getMessage());
        }
    }

    private static void loadExpensesFromFile() {
        expenses.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                double amount = Double.parseDouble(parts[1]);
                String category = parts[2];
                expenses.add(new Expense(name, amount, category));
            }
            System.out.println("Expenses loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading expenses from file: " + e.getMessage());
        }
    }
}