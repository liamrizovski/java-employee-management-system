package com.example.dep;

/*--------------------
 Student name: Liam Rizovski
 Subject code: CSIT213
 --------------------*/

import java.util.Formatter;
import java.util.Scanner;

/**
 * Represents a project in the DEP system.
 */
public class Project implements DataIO {

    private int    number;
    private String title;
    private String sponsor;
    private int    dNumber;
    private double budget;

    /** Default constructor */
    public Project() {}

    /**
     * Parameterised constructor.
     *
     * @param number  project number
     * @param title   project title
     * @param sponsor sponsoring organisation
     * @param dNumber department number responsible
     * @param budget  project budget
     */
    public Project(int number, String title, String sponsor, int dNumber, double budget) {
        this.number  = number;
        this.title   = title;
        this.sponsor = sponsor;
        this.dNumber = dNumber;
        this.budget  = budget;
    }

    /** @return project number */
    public int getNumber() {
        return number;
    }

    /** @return project title */
    public String getTitle() {
        return title;
    }

    /** @return sponsor name */
    public String getSponsor() {
        return sponsor;
    }

    /** @return department number */
    public int getDNumber() {
        return dNumber;
    }

    /** @return project budget */
    public double getBudget() {
        return budget;
    }

    /**
     * Reads project data from a Scanner.
     * Format: number, title, sponsor, dNumber, budget
     * Note: title and sponsor may contain spaces — handled by reading tokens
     * until dNumber (integer) is found.
     */
    @Override
    public void dataInput(Scanner scanner) {
        number = scanner.nextInt();

        // Read title tokens until we encounter a token that starts a sponsor
        // The simplest approach: read the rest of the line and split on ", "
        // But since Scanner delimiter is already set, we read tokens and detect ints.
        // Title: one or two words, Sponsor: may have spaces, then int, then double.
        // Strategy: read the remaining line and parse manually.
        String rest = scanner.nextLine().trim(); // e.g. "Computation, Microsoft, 8, 25000"
        String[] parts = rest.split(", ");
        // parts[0] = title (may be multi-word if title has commas — unlikely but safe)
        // parts[last-2] = dNumber, parts[last-1] = budget, rest = sponsor
        // We know last two are dNumber and budget (both numeric)
        title   = parts[0];
        dNumber = Integer.parseInt(parts[parts.length - 2]);
        budget  = Double.parseDouble(parts[parts.length - 1]);
        // Sponsor is everything between title and the last two fields
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < parts.length - 2; i++) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(parts[i]);
        }
        sponsor = sb.toString();
    }

    /**
     * Writes project data using a Formatter.
     */
    @Override
    public void dataOutput(Formatter formatter) {
        formatter.format("%d, %s, %s, %d, %.0f%n", number, title, sponsor, dNumber, budget);
    }

    /**
     * Returns a string representation of this project.
     */
    @Override
    public String toString() {
        return String.format(
            "Project Number: %d%nTitle: %s%nSponsor: %s%nDepartment: %d%nBudget: %.2f",
            number, title, sponsor, dNumber, budget);
    }
}
