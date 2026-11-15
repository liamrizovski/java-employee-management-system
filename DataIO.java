package com.example.dep;

/*--------------------
 Student name: Liam Rizovski
 Subject code: CSIT213
 --------------------*/

import java.util.Formatter;
import java.util.Scanner;

/**
 * DataIO interface defining methods for data input/output operations.
 */
public interface DataIO {
    void dataInput(Scanner scanner);
    void dataOutput(Formatter formatter);
    String toString();
}
