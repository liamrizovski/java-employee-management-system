package com.example.dep;

/*--------------------
 Student name: Liam Rizovski
 Subject code: CSIT213
 --------------------*/

import java.util.Formatter;
import java.util.Scanner;

/**
 * Represents a works-on record linking an employee to a project with hours worked.
 */
public class WorksOn implements DataIO {

    private int eNumber; // employee number
    private int pNumber; // project number
    private int hours;   // hours worked

    /** Default constructor */
    public WorksOn() {}

    /**
     * Parameterised constructor.
     *
     * @param eNumber employee number
     * @param pNumber project number
     * @param hours   hours worked
     */
    public WorksOn(int eNumber, int pNumber, int hours) {
        this.eNumber = eNumber;
        this.pNumber = pNumber;
        this.hours   = hours;
    }

    /** @return employee number */
    public int getENumber() {
        return eNumber;
    }

    /** @return project number */
    public int getPNumber() {
        return pNumber;
    }

    /** @return hours worked */
    public int getHours() {
        return hours;
    }

    /** @param hours new hours value */
    public void setHours(int hours) {
        this.hours = hours;
    }

    /**
     * Reads works-on data from a Scanner.
     * Format: eNumber, pNumber, hours
     */
    @Override
    public void dataInput(Scanner scanner) {
        eNumber = scanner.nextInt();
        pNumber = scanner.nextInt();
        hours   = scanner.nextInt();
    }

    /**
     * Writes works-on data using a Formatter.
     */
    @Override
    public void dataOutput(Formatter formatter) {
        formatter.format("%d, %d, %d%n", eNumber, pNumber, hours);
    }

    /**
     * Returns a string representation of this works-on record.
     */
    @Override
    public String toString() {
        return String.format(
            "Employee: %d%nProject: %d%nHours: %d",
            eNumber, pNumber, hours);
    }
}
