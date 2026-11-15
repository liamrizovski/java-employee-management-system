package com.example.dep;

/*--------------------
 Student name: Liam Rizovski
 Subject code: CSIT213
 --------------------*/

import java.util.Formatter;
import java.util.Scanner;

/**
 * Represents a Developer employee.
 * Developers have a programming languages field.
 */
public class Developer extends Employee {

    private String languages;

    /** Default constructor */
    public Developer() {
        super();
    }

    /**
     * Parameterised constructor.
     *
     * @param number     employee number
     * @param name       employee name
     * @param dob        date of birth
     * @param address    home address
     * @param gender     gender
     * @param salary     salary
     * @param supervisor supervisor number
     * @param deptNumber department number
     * @param languages  programming languages (space-separated)
     */
    public Developer(int number, String name, String dob, String address,
                     String gender, double salary, int supervisor, int deptNumber, String languages) {
        super(number, name, dob, address, gender, salary, supervisor, deptNumber);
        this.languages = languages;
    }

    /** @return programming languages string */
    public String getLanguages() {
        return languages;
    }

    /**
     * Reads developer-specific data (languages) after common employee fields.
     */
    @Override
    public void dataInput(Scanner scanner) {
        super.dataInput(scanner);
        languages = scanner.nextLine().trim();
    }

    /**
     * Writes the full Developer record using a Formatter.
     */
    @Override
    public void dataOutput(Formatter formatter) {
        formatter.format("D, %d, %s, %s, %s, %s, %.1f, %d, %d, %s%n",
            number, name, dob, address, gender, salary, supervisor, deptNumber, languages);
    }

    /**
     * Returns a string representation of this Developer employee.
     */
    @Override
    public String toString() {
        return super.toString() + String.format("%nType: Developer%nLanguages: %s", languages);
    }
}
