import java.util.ArrayList;
import java.util.Scanner;

// Define the Expense class
class Expense {
    private String name;
    private double amount;

    public Expense(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    // getter for each variable
    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    // Override the toString() method
    public String toString() {
        return name + " - $" + String.format("%.2f", amount);
    }
}

public class ExpenseTracker {

    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Expense> expenses = new ArrayList<>();
        boolean running = true;

        System.out.println("Welcome to the Simple Expense Tracker");

        // main program loop
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Add expense");
            System.out.println("2. View expenses");
            System.out.println("3. Show total spent");
            System.out.println("4. Show average expense");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
                continue;
            }

            // switch case
            switch (choice) {
                case 1:
                    System.out.print("Enter expense name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter amount spent: ");
                    if (scanner.hasNextDouble()) {
                        double amount = scanner.nextDouble();
                        scanner.nextLine();

                        if (amount < 0) {
                            System.out.println("Amount cannot be negative.");
                        } else {
                            expenses.add(new Expense(name, amount));
                            System.out.println("Expense added successfully.");
                        }
                    } else {
                        System.out.println("Invalid amount.");
                        scanner.nextLine();
                    }
                    break;

                case 2:
                    if (expenses.isEmpty()) {
                        System.out.println("No expenses entered yet.");
                    } else {
                        System.out.println("\nExpenses:");
                        for (int i = 0; i < expenses.size(); i++) {
                            System.out.println((i + 1) + ". " + expenses.get(i));
                        }
                    }
                    break;

                case 3:
                    double total = 0;
                    for (Expense expense : expenses) {
                        total += expense.getAmount();
                    }
                    System.out.println("Total spent: $" + String.format("%.2f", total));
                    break;

                case 4:
                    if (expenses.isEmpty()) {
                        System.out.println("No expenses to calculate average.");
                    } else {
                        double sum = 0;
                        for (Expense expense : expenses) {
                            sum += expense.getAmount();
                        }
                        double average = sum / expenses.size();
                        System.out.println("Average expense: $" + String.format("%.2f", average));
                    }
                    break;

                case 5:
                    running = false;
                    System.out.println("Goodbye.");
                    break;

                default:
                    System.out.println("Please choose a number from 1 to 5.");
            }
        }

        // close the scanner
        scanner.close();
    }
}