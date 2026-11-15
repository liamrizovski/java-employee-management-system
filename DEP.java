package com.example.dep;

/*--------------------
 Student name: Liam Rizovski
 Subject code: CSIT213
 --------------------*/

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Optional;
import java.util.Scanner;

/**
 * DEP - Department, Employee, and Project management system.
 * Main JavaFX application class.
 */
public class DEP extends Application {

    // ── Data containers ──────────────────────────────────────────────────────
    private ArrayList<Department> departments = new ArrayList<>();
    private ArrayList<Employee>   employees   = new ArrayList<>();
    private ArrayList<Project>    projects    = new ArrayList<>();
    private ArrayList<WorksOn>    workson     = new ArrayList<>();

    // ── Department section ───────────────────────────────────────────────────
    private ListView<String> deptListView    = new ListView<>();
    private TextField tfDeptName            = new TextField();
    private TextField tfDeptManager         = new TextField();
    private TextField tfDeptBudget          = new TextField();
    private TextField tfDeptStartDate       = new TextField();

    // ── Employee section ─────────────────────────────────────────────────────
    private ListView<String> empListView    = new ListView<>();
    private TextField tfEmpName            = new TextField();
    private TextField tfEmpDob             = new TextField();
    private TextField tfEmpAddress         = new TextField();
    private TextField tfEmpGender          = new TextField();
    private TextField tfEmpSalary          = new TextField();
    private TextField tfEmpSupervisor      = new TextField();
    private TextField tfEmpDept            = new TextField();
    private TextField tfEmpSkillLang       = new TextField();

    // ── Project section ──────────────────────────────────────────────────────
    private ListView<String> projListView  = new ListView<>();
    private TextField tfProjTitle          = new TextField();
    private TextField tfProjSponsor        = new TextField();
    private TextField tfProjDept           = new TextField();
    private TextField tfProjBudget         = new TextField();

    // ── Works-on section ─────────────────────────────────────────────────────
    private ListView<String> woListView    = new ListView<>();
    private TextField tfWoEmpNum           = new TextField();
    private TextField tfWoProjNum          = new TextField();
    private TextField tfWoHours            = new TextField();
    private TextField tfMessage            = new TextField();

    // ── Buttons ───────────────────────────────────────────────────────────────
    private Button btnAdd    = new Button("Add");
    private Button btnDelete = new Button("Delete");
    private Button btnSave   = new Button("Save");

    // ── Entry point ──────────────────────────────────────────────────────────
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * JavaFX start method — builds the GUI and loads data.
     */
    @Override
    public void start(Stage primaryStage) {
        init();
        startStage(primaryStage);
    }

    /** Initialises data by loading all four files. */
    public void init() {
        loadDepartments();
        loadEmployees();
        loadProjects();
        loadWorksOn();
    }

    /** Builds and shows the primary stage. */
    public void startStage(Stage stage) {
        stage.setTitle("Departments, employees and projects management system");

        // Top row: Dept | Employee | Project | WorksOn panels
        HBox topRow = new HBox(10,
            buildDeptPanel(),
            buildEmpPanel(),
            buildProjPanel(),
            buildWorksOnPanel()
        );
        topRow.setPadding(new Insets(10));

        // Bottom: pie chart
        PieChart pieChart = buildPieChart();

        VBox root = new VBox(10, topRow, pieChart);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 1200, 780);
        stage.setScene(scene);
        stage.show();

        // Wire list-click handlers
        wireListeners();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Data loading
    // ─────────────────────────────────────────────────────────────────────────

    /** Loads departments from departments.txt */
    public void loadDepartments() {
        try (Scanner sc = new Scanner(new File("departments.txt"))) {
            sc.useDelimiter(", |\r\n|\n");
            while (sc.hasNextInt()) {
                int    number    = sc.nextInt();
                String name      = sc.next();
                int    manager   = sc.nextInt();
                double budget    = sc.nextDouble();
                String startDate = sc.next();
                departments.add(new Department(number, name, manager, budget, startDate));
            }
        } catch (FileNotFoundException e) {
            System.err.println("departments.txt not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error loading departments: " + e.getMessage());
        }

        // Populate list view
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Department d : departments) {
            items.add(String.valueOf(d.getNumber()));
        }
        deptListView.setItems(items);
    }

    /** Loads employees from employees.txt */
    public void loadEmployees() {
        try (Scanner sc = new Scanner(new File("employees.txt"))) {
            sc.useDelimiter(", |\r\n|\n");
            while (sc.hasNext()) {
                String type = sc.next().trim();
                if (type.isEmpty()) continue;

                int    number     = sc.nextInt();
                String name       = sc.next();
                String dob        = sc.next();

                // Read address (multi-token until single-char gender M/F)
                StringBuilder addrSb = new StringBuilder();
                String token = sc.next();
                while (token.length() != 1 ||
                       (token.charAt(0) != 'M' && token.charAt(0) != 'F')) {
                    if (addrSb.length() > 0) addrSb.append(", ");
                    addrSb.append(token);
                    token = sc.next();
                }
                String address    = addrSb.toString();
                String gender     = token;
                double salary     = sc.nextDouble();
                int    supervisor = sc.nextInt();
                int    deptNumber = sc.nextInt();

                // Skills/languages — rest of line
                String extra = sc.hasNextLine() ? sc.nextLine().trim() : "";

                if (type.equals("A")) {
                    employees.add(new Admin(number, name, dob, address,
                            gender, salary, supervisor, deptNumber, extra));
                } else if (type.equals("D")) {
                    employees.add(new Developer(number, name, dob, address,
                            gender, salary, supervisor, deptNumber, extra));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("employees.txt not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error loading employees: " + e.getMessage());
        }

        ObservableList<String> items = FXCollections.observableArrayList();
        for (Employee e : employees) {
            items.add(String.valueOf(e.getNumber()));
        }
        empListView.setItems(items);
    }

    /** Loads projects from projects.txt */
    public void loadProjects() {
        try (Scanner sc = new Scanner(new File("projects.txt"))) {
            sc.useDelimiter(", |\r\n|\n");
            while (sc.hasNextInt()) {
                int    number  = sc.nextInt();
                // Read title and sponsor tokens; last two are dNumber (int) and budget (double)
                // Strategy: read rest of line and parse
                String rest = sc.hasNextLine() ? sc.nextLine().trim() : "";
                String[] parts = rest.split(", ");
                String title   = parts[0];
                int    dNumber = Integer.parseInt(parts[parts.length - 2]);
                double budget  = Double.parseDouble(parts[parts.length - 1]);
                StringBuilder sp = new StringBuilder();
                for (int i = 1; i < parts.length - 2; i++) {
                    if (sp.length() > 0) sp.append(", ");
                    sp.append(parts[i]);
                }
                String sponsor = sp.toString();
                projects.add(new Project(number, title, sponsor, dNumber, budget));
            }
        } catch (FileNotFoundException e) {
            System.err.println("projects.txt not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error loading projects: " + e.getMessage());
        }

        ObservableList<String> items = FXCollections.observableArrayList();
        for (Project p : projects) {
            items.add(String.valueOf(p.getNumber()));
        }
        projListView.setItems(items);
    }

    /** Loads works-on records from workson.txt */
    public void loadWorksOn() {
        try (Scanner sc = new Scanner(new File("workson.txt"))) {
            sc.useDelimiter(", |\r\n|\n");
            while (sc.hasNextInt()) {
                int eNum  = sc.nextInt();
                int pNum  = sc.nextInt();
                int hours = sc.nextInt();
                workson.add(new WorksOn(eNum, pNum, hours));
            }
        } catch (FileNotFoundException e) {
            System.err.println("workson.txt not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error loading workson: " + e.getMessage());
        }
        refreshWorksOnList();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GUI panel builders
    // ─────────────────────────────────────────────────────────────────────────

    /** Builds the Department information panel. */
    private VBox buildDeptPanel() {
        deptListView.setPrefSize(120, 120);
        VBox detail = buildLabeledFields(
            new String[]{"Name", "Manager", "Budget", "Start date"},
            new TextField[]{tfDeptName, tfDeptManager, tfDeptBudget, tfDeptStartDate});

        Label header = new Label("Department  Information");
        Label numLbl = new Label("Department\nNumber");
        VBox panel = new VBox(5, header,
            new HBox(5, new VBox(5, numLbl, deptListView), detail));
        panel.setPadding(new Insets(5));
        panel.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1;");
        return panel;
    }

    /** Builds the Employee information panel. */
    private VBox buildEmpPanel() {
        empListView.setPrefSize(100, 120);
        VBox detail = buildLabeledFields(
            new String[]{"Name", "DOB", "Address", "Gender", "Salary", "Supervisor",
                         "Department", "Skill/Language"},
            new TextField[]{tfEmpName, tfEmpDob, tfEmpAddress, tfEmpGender,
                            tfEmpSalary, tfEmpSupervisor, tfEmpDept, tfEmpSkillLang});

        Label header = new Label("Employee  Information");
        Label numLbl = new Label("Employee\nNumber");
        VBox panel = new VBox(5, header,
            new HBox(5, new VBox(5, numLbl, empListView), detail));
        panel.setPadding(new Insets(5));
        panel.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1;");
        return panel;
    }

    /** Builds the Project information panel. */
    private VBox buildProjPanel() {
        projListView.setPrefSize(100, 120);
        VBox detail = buildLabeledFields(
            new String[]{"Title", "Sponsor", "Department", "Budget"},
            new TextField[]{tfProjTitle, tfProjSponsor, tfProjDept, tfProjBudget});

        Label header = new Label("Project  Information");
        Label numLbl = new Label("Project\nNumber");
        VBox panel = new VBox(5, header,
            new HBox(5, new VBox(5, numLbl, projListView), detail));
        panel.setPadding(new Insets(5));
        panel.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1;");
        return panel;
    }

    /** Builds the Works-on information panel with Add/Delete/Save buttons. */
    private VBox buildWorksOnPanel() {
        woListView.setPrefSize(160, 120);

        // Detail fields
        VBox detail = buildLabeledFields(
            new String[]{"Employee\nnumber", "Project\nnumber", "Hours"},
            new TextField[]{tfWoEmpNum, tfWoProjNum, tfWoHours});

        // Buttons row
        HBox buttons = new HBox(5, btnAdd, btnDelete, btnSave);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        // Message field
        VBox msgBox = new VBox(3, new Label("Message"), tfMessage);

        Label header = new Label("Works on  Information");
        Label woLbl  = new Label("Works on");

        VBox panel = new VBox(5, header,
            new HBox(5, new VBox(5, woLbl, woListView), detail),
            buttons, msgBox);
        panel.setPadding(new Insets(5));
        panel.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1;");
        return panel;
    }

    /**
     * Helper: creates a VBox of label + TextField pairs.
     *
     * @param labels label strings
     * @param fields corresponding TextFields
     * @return VBox containing the label-field grid
     */
    private VBox buildLabeledFields(String[] labels, TextField[] fields) {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(3);
        for (int i = 0; i < labels.length; i++) {
            grid.add(new Label(labels[i]), 0, i);
            fields[i].setPrefWidth(150);
            fields[i].setEditable(false);
            grid.add(fields[i], 1, i);
        }
        return new VBox(grid);
    }

    /** Builds the department budget pie chart. */
    private PieChart buildPieChart() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (Department d : departments) {
            data.add(new PieChart.Data(d.getNumber() + " " + d.getName(), d.getBudget()));
        }
        PieChart chart = new PieChart(data);
        chart.setTitle("Department Budgets");
        chart.setPrefHeight(330);
        return chart;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Event wiring
    // ─────────────────────────────────────────────────────────────────────────

    /** Wires all list selection and button event handlers. */
    private void wireListeners() {

        // Department list selection
        deptListView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> {
                if (newVal == null) return;
                int num = Integer.parseInt(newVal.trim());
                for (Department d : departments) {
                    if (d.getNumber() == num) {
                        tfDeptName.setText(d.getName());
                        tfDeptManager.setText(String.valueOf(d.getManager()));
                        tfDeptBudget.setText(String.format("%.2f", d.getBudget()));
                        tfDeptStartDate.setText(d.getStartDate());
                        break;
                    }
                }
            });

        // Employee list selection
        empListView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> {
                if (newVal == null) return;
                int num = Integer.parseInt(newVal.trim());
                for (Employee e : employees) {
                    if (e.getNumber() == num) {
                        tfEmpName.setText(e.getName());
                        tfEmpDob.setText(e.getDob());
                        tfEmpAddress.setText(e.getAddress());
                        tfEmpGender.setText(e.getGender());
                        tfEmpSalary.setText(String.valueOf(e.getSalary()));
                        tfEmpSupervisor.setText(String.valueOf(e.getSupervisor()));
                        tfEmpDept.setText(String.valueOf(e.getDeptNumber()));
                        if (e instanceof Developer) {
                            tfEmpSkillLang.setText(((Developer) e).getLanguages());
                        } else if (e instanceof Admin) {
                            tfEmpSkillLang.setText(((Admin) e).getSkills());
                        }
                        break;
                    }
                }
                // Populate works-on employee number field for Add/Delete
                tfWoEmpNum.setText(newVal.trim());
            });

        // Project list selection
        projListView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> {
                if (newVal == null) return;
                int num = Integer.parseInt(newVal.trim());
                for (Project p : projects) {
                    if (p.getNumber() == num) {
                        tfProjTitle.setText(p.getTitle());
                        tfProjSponsor.setText(p.getSponsor());
                        tfProjDept.setText(String.valueOf(p.getDNumber()));
                        tfProjBudget.setText(String.format("%.2f", p.getBudget()));
                        break;
                    }
                }
                tfWoProjNum.setText(newVal.trim());
            });

        // Works-on list selection — populate detail fields
        woListView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> {
                if (newVal == null) return;
                // Format: "eNum, pNum, hours"
                String[] parts = newVal.split(", ");
                if (parts.length >= 3) {
                    tfWoEmpNum.setText(parts[0].trim());
                    tfWoProjNum.setText(parts[1].trim());
                    tfWoHours.setText(parts[2].trim());
                }
            });

        // Add button
        btnAdd.setOnAction(e -> handleAdd());

        // Delete button
        btnDelete.setOnAction(e -> handleDelete());

        // Save button
        btnSave.setOnAction(e -> handleSave());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Button handlers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Handles the Add button click.
     * Validates employee is a Developer, checks for duplicate, prompts for hours.
     */
    private void handleAdd() {
        tfMessage.clear();

        String empStr  = tfWoEmpNum.getText().trim();
        String projStr = tfWoProjNum.getText().trim();

        if (empStr.isEmpty() || projStr.isEmpty()) {
            tfMessage.setText("Please select an employee and a project.");
            return;
        }

        int selectedEmp;
        int selectedProj;
        try {
            selectedEmp  = Integer.parseInt(empStr);
            selectedProj = Integer.parseInt(projStr);
        } catch (NumberFormatException ex) {
            tfMessage.setText("Invalid employee or project selection.");
            return;
        }

        // Check employee is a Developer
        Employee found = null;
        for (Employee e : employees) {
            if (e.getNumber() == selectedEmp) {
                found = e;
                break;
            }
        }

        if (!(found instanceof Developer)) {
            tfMessage.setText("Please select a developer");
            return;
        }

        // Check for duplicate works-on record
        for (WorksOn wo : workson) {
            if (wo.getENumber() == selectedEmp && wo.getPNumber() == selectedProj) {
                tfMessage.setText("Employee " + selectedEmp +
                    " has already worked on Project " + selectedProj);
                return;
            }
        }

        // Prompt for hours
        TextInputDialog dialog = new TextInputDialog("10");
        dialog.setTitle("Confirmation");
        dialog.setHeaderText("Enter hours");
        dialog.setContentText("Hours:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(hoursStr -> {
            try {
                int hours = Integer.parseInt(hoursStr.trim());
                WorksOn newWo = new WorksOn(selectedEmp, selectedProj, hours);
                workson.add(newWo);
                refreshWorksOnList();
                tfMessage.setText("Employee " + selectedEmp +
                    " works on " + selectedProj + " is added");
            } catch (NumberFormatException ex) {
                tfMessage.setText("Invalid hours value.");
            }
        });
    }

    /**
     * Handles the Delete button click.
     * Confirms deletion of the selected works-on record.
     */
    private void handleDelete() {
        tfMessage.clear();
        String selected = woListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            tfMessage.setText("Please select a works-on record to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm the removal");
        confirm.setHeaderText("Confirmation");
        confirm.setContentText("Are you sure?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String[] parts = selected.split(", ");
            if (parts.length >= 3) {
                int eNum = Integer.parseInt(parts[0].trim());
                int pNum = Integer.parseInt(parts[1].trim());
                workson.removeIf(wo ->
                    wo.getENumber() == eNum && wo.getPNumber() == pNum);
                refreshWorksOnList();
                tfMessage.setText("The selected works-on record has been deleted");
            }
        }
    }

    /**
     * Handles the Save button click.
     * Saves all works-on records to workson.txt.
     */
    private void handleSave() {
        tfMessage.clear();
        try (Formatter fmt = new Formatter("workson.txt")) {
            for (WorksOn wo : workson) {
                wo.dataOutput(fmt);
            }
            tfMessage.setText(workson.size() + " works-on records have been saved");
        } catch (Exception e) {
            tfMessage.setText("Error saving: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Utility
    // ─────────────────────────────────────────────────────────────────────────

    /** Refreshes the works-on ListView from the workson container. */
    private void refreshWorksOnList() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (WorksOn wo : workson) {
            items.add(wo.getENumber() + ", " + wo.getPNumber() + ", " + wo.getHours());
        }
        woListView.setItems(items);
    }
}
