package com.example.dep;

/*--------------------
 Student name: Liam Rizovski
 Subject code: CSIT213
 --------------------*/

import java.util.Formatter;
import java.util.Scanner;

/**
 * Represents an Admin employee.
 * Admins have a skills field instead of programming languages.
 */
public class Admin extends Employee {

    private String skills;

    /** Default constructor */
    public Admin() {
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
     * @param skills     admin skills (space-separated list)
     */
    public Admin(int number, String name, String dob, String address,
                 String gender, double salary, int supervisor, int deptNumber, String skills) {
        super(number, name, dob, address, gender, salary, supervisor, deptNumber);
        this.skills = skills;
    }

    /** @return skills string */
    public String getSkills() {
        return skills;
    }

    /**
     * Reads admin-specific data (skills) after common employee fields are loaded.
     * The caller passes a Scanner already positioned at the skills token.
     */
    @Override
    public void dataInput(Scanner scanner) {
        super.dataInput(scanner);
        // Read remaining line as skills (may contain spaces — read rest of tokens on line)
        skills = scanner.nextLine().trim();
    }

    /**
     * Writes the full Admin record using a Formatter.
     */
    @Override
    public void dataOutput(Formatter formatter) {
        formatter.format("A, %d, %s, %s, %s, %s, %.1f, %d, %d, %s%n",
            number, name, dob, address, gender, salary, supervisor, deptNumber, skills);
    }

    /**
     * Returns a string representation of this Admin employee.
     */
    @Override
    public String toString() {
        return super.toString() + String.format("%nType: Admin%nSkills: %s", skills);
    }
}
