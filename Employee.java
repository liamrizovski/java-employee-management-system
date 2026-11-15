package com.example.dep;

/*--------------------
 Student name: Liam Rizovski
 Subject code: CSIT213
 --------------------*/

import java.util.Formatter;
import java.util.Scanner;

/**
 * Abstract base class representing an employee in the DEP system.
 * Subclasses Admin and Developer extend this class.
 */
public abstract class Employee implements DataIO {

    protected int    number;
    protected String name;
    protected String dob;
    protected String address;
    protected String gender;
    protected double salary;
    protected int    supervisor;
    protected int    deptNumber;

    /** Default constructor */
    public Employee() {}

    /**
     * Parameterised constructor.
     *
     * @param number     employee number
     * @param name       employee name
     * @param dob        date of birth
     * @param address    home address
     * @param gender     gender (M/F)
     * @param salary     weekly salary
     * @param supervisor supervisor employee number (0 if none)
     * @param deptNumber department number
     */
    public Employee(int number, String name, String dob, String address,
                    String gender, double salary, int supervisor, int deptNumber) {
        this.number     = number;
        this.name       = name;
        this.dob        = dob;
        this.address    = address;
        this.gender     = gender;
        this.salary     = salary;
        this.supervisor = supervisor;
        this.deptNumber = deptNumber;
    }

    /** @return employee number */
    public int getNumber() {
        return number;
    }

    /** @return employee name */
    public String getName() {
        return name;
    }

    /** @return date of birth */
    public String getDob() {
        return dob;
    }

    /** @return home address */
    public String getAddress() {
        return address;
    }

    /** @return gender */
    public String getGender() {
        return gender;
    }

    /** @return salary */
    public double getSalary() {
        return salary;
    }

    /** @return supervisor employee number */
    public int getSupervisor() {
        return supervisor;
    }

    /** @return department number */
    public int getDeptNumber() {
        return deptNumber;
    }

    /**
     * Reads common employee fields from a Scanner.
     * Subclasses should call this then read their own field (skills/languages).
     *
     * File format (comma+space delimited):
     *   type, number, name, dob, address (multi-word), gender, salary, supervisor, deptNumber, skills/languages
     *
     * Note: address contains spaces and commas so it must be handled specially.
     * The Scanner delimiter is set to ", |\r\n|\n" by the caller, so each token is one field.
     * However, the address field spans multiple comma-separated tokens.
     * We rebuild the address from tokens until we hit a single-letter gender token.
     */
    @Override
    public void dataInput(Scanner scanner) {
        // number, name, dob already consumed by DEP loader before calling this method.
        // This method reads: address (multi-token), gender, salary, supervisor, deptNumber
        // and then the subclass reads skills/languages.

        // Read address tokens until we find the gender token (single character M or F)
        StringBuilder addrBuilder = new StringBuilder();
        String token = scanner.next();
        while (token.length() != 1 || (token.charAt(0) != 'M' && token.charAt(0) != 'F')) {
            if (addrBuilder.length() > 0) addrBuilder.append(", ");
            addrBuilder.append(token);
            token = scanner.next();
        }
        address = addrBuilder.toString();
        gender  = token;
        salary     = scanner.nextDouble();
        supervisor = scanner.nextInt();
        deptNumber = scanner.nextInt();
    }

    /**
     * Writes common employee fields using a Formatter.
     * Subclasses append their own field.
     */
    @Override
    public void dataOutput(Formatter formatter) {
        // Written by subclass which calls this helper first via super or builds the full line.
    }

    /**
     * Returns a string representation of common employee fields.
     */
    @Override
    public String toString() {
        return String.format(
            "Employee Number: %d%nName: %s%nDOB: %s%nAddress: %s%nGender: %s%n" +
            "Salary: %.1f%nSupervisor: %d%nDepartment: %d",
            number, name, dob, address, gender, salary, supervisor, deptNumber);
    }
}
