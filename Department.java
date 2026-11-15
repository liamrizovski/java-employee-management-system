package com.example.dep;

/*--------------------
 Student name: Liam Rizovski
 Subject code: CSIT213
 --------------------*/

import java.util.Formatter;
import java.util.Scanner;

/**
 * Represents a department in the DEP system.
 */
public class Department implements DataIO {

    private int number;
    private String name;
    private int manager;
    private double budget;
    private String startDate;

    /** Default constructor */
    public Department() {}

    /**
     * Parameterised constructor.
     *
     * @param number    department number
     * @param name      department name
     * @param manager   manager employee number
     * @param budget    department budget
     * @param startDate department start date string
     */
    public Department(int number, String name, int manager, double budget, String startDate) {
        this.number    = number;
        this.name      = name;
        this.manager   = manager;
        this.budget    = budget;
        this.startDate = startDate;
    }

    /** @return department number */
    public int getNumber() {
        return number;
    }

    /** @return department name */
    public String getName() {
        return name;
    }

    /** @return manager employee number */
    public int getManager() {
        return manager;
    }

    /** @return department budget */
    public double getBudget() {
        return budget;
    }

    /** @return department start date */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Reads department data from a Scanner.
     * Format: number, name, manager, budget, startDate
     */
    @Override
    public void dataInput(Scanner scanner) {
        number    = scanner.nextInt();
        name      = scanner.next();
        manager   = scanner.nextInt();
        budget    = scanner.nextDouble();
        startDate = scanner.next();
    }

    /**
     * Writes department data using a Formatter.
     */
    @Override
    public void dataOutput(Formatter formatter) {
        formatter.format("%d, %s, %d, %.2f, %s%n", number, name, manager, budget, startDate);
    }

    /**
     * Returns a string representation of this department.
     */
    @Override
    public String toString() {
        return String.format(
            "Department Number: %d%nName: %s%nManager: %d%nBudget: %.2f%nStart Date: %s",
            number, name, manager, budget, startDate);
    }
}
