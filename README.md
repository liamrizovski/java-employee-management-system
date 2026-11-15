# Java Employee & Project Management System

A Java-based employee and project management application developed as part of the **CSIT213 Java Programming** subject at the University of Wollongong.

This project demonstrates object-oriented programming principles through the implementation of classes representing employees, departments, projects, and workplace relationships.

## Overview

The application models an organisational structure, including employee information, departments, projects, and employee-project assignments.

The project focuses on applying fundamental Java programming concepts to a structured management system.

## Technologies Used

- **Language:** Java
- **Programming Paradigm:** Object-Oriented Programming (OOP)
- **Development Concepts:** Classes, Objects, Inheritance, Encapsulation, Interfaces, and Method Overriding
- **Java Utilities:** Scanner, Formatter, and ArrayList

## Key Features

- Representation of employee and department information
- Support for different employee roles, including administrators and developers
- Management of project-related information
- Representation of relationships between employees and projects
- Structured data input and formatted output
- Modular code organisation using multiple Java classes

## Object-Oriented Programming Concepts

### Encapsulation
Employee, department, and project information is organised into classes with attributes and methods.

### Inheritance
Specialised employee classes extend the base employee structure to represent different roles.

### Interfaces
A shared interface defines methods for data input and output.

### Method Overriding
Subclasses provide specialised implementations of inherited methods where appropriate.

### Object Relationships
The system models relationships between employees, departments, and projects.

## Project Structure

| File | Description |
|---|---|
| `Admin.java` | Represents an administrative employee role |
| `DataIO.java` | Defines an interface for data input and output |
| `DEP.java` | Contains application-level management logic |
| `Department.java` | Represents department information |
| `Developer.java` | Represents a developer employee role |
| `Employee.java` | Defines the employee data model |
| `Project.java` | Represents project information |
| `WorksOn.java` | Represents employee-project relationships |

## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- A Java-compatible IDE or terminal

### Running the Project

1. Clone the repository:

   ```bash
   git clone https://github.com/liamrizovski/java-employee-management-system.git
   ```

2. Navigate to the project directory:

   ```bash
   cd java-employee-management-system
   ```

3. Compile the Java source files:

   ```bash
   javac *.java
   ```

4. Run the application:

   ```bash
   java DEP
   ```

**Note:** Execution may depend on the availability of any external data files or resources referenced by the source code.

## Learning Outcomes

Through this project, I gained practical experience in:

- Designing Java applications using object-oriented principles
- Implementing inheritance and interfaces
- Organising code into reusable classes
- Modelling relationships between software objects
- Working with Java collections and input/output utilities
- Developing and maintaining a multi-class Java application

## Academic Context

This project was developed for **CSIT213 Java Programming** at the **University of Wollongong**.

The repository contains my own Java source code and original project documentation. University-provided assignment materials have been excluded.

Permission to publish my original Java code was granted by the subject lecturer.

## Author

**Liam Rizovski**  
Bachelor of Computer Science — Artificial Intelligence & Big Data  
University of Wollongong

GitHub: https://github.com/liamrizovski
