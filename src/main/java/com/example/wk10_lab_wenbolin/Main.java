package com.example.wk10_lab_wenbolin;

import java.sql.SQLException;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class Main extends Application {

    private final EmploymentApplicationDao applicationDao = new EmploymentApplicationDao();

    private TextField txtFullName;
    private TextField txtAddress;
    private TextField txtContact;
    private TextField txtEmail;
    private TextField txtEducation;
    private ToggleGroup genderGroup;
    private DatePicker dpDateAvailable;
    private TextField txtPosition;
    private TextField txtSalary;
    private ToggleGroup authGroup;
    private ToggleGroup relGroup;
    private TextField txtRelExplain;
    private TextField txtSignature;

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(12);
        grid.setVgap(10);

        int row = 0;
        addSectionTitle(grid, "John Company - Employment Application", 18, row++);

        // --- Section 1: Personal Information ---
        addSectionTitle(grid, "Personal Information", 14, row++);
        txtFullName = addTextField(grid, "Full Name:", row++);
        txtAddress = addTextField(grid, "Current Address:", row++);
        txtContact = addTextField(grid, "Contact Number:", row++);
        txtEmail = addTextField(grid, "Email Address:", row++);
        txtEducation = addTextField(grid, "Highest Educational Attainment:", row++);
        genderGroup = addRadioRow(grid, "Gender:", row++, "Male", "Female", "Other");

        // --- Section 2: Employment Eligibility ---
        addSectionTitle(grid, "Employment Eligibility", 14, row++);
        dpDateAvailable = new DatePicker(LocalDate.now());
        addRow(grid, "Date Available:", dpDateAvailable, row++);
        txtPosition = addTextField(grid, "Desired Position:", row++);
        txtSalary = addTextField(grid, "Desired Salary:", row++);
        txtSalary.setPromptText("e.g. 65000");
        authGroup = addRadioRow(grid, "Legally authorized to work in the country?", row++, "Yes", "No");
        relGroup = addRadioRow(grid, "Relatives working for our company?", row++, "Yes", "No");
        txtRelExplain = addTextField(grid, "If yes, please explain further:", row++);

        // --- Signature & Submit ---
        txtSignature = addTextField(grid, "Applicant's Signature:", row++);
        Button btnSubmit = new Button("Submit");
        btnSubmit.setPrefWidth(120);
        grid.add(btnSubmit, 1, row);
        btnSubmit.setOnAction(e -> handleSubmit());

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);

        primaryStage.setTitle("Employment Application - Wk10 Lab");
        primaryStage.setScene(new Scene(scrollPane, 600, 650));
        primaryStage.show();
    }

    private void handleSubmit() {
        String fullName = txtFullName.getText().trim();
        if (StringPool.BLANK.equals(fullName)) {
            alert(AlertType.WARNING, "Validation Error", "Full Name cannot be empty!");
            return;
        }

        double salary = 0.0;
        String salaryText = txtSalary.getText().trim();
        if (!StringPool.BLANK.equals(salaryText)) {
            try {
                salary = Double.parseDouble(salaryText);
            } catch (NumberFormatException ex) {
                alert(AlertType.ERROR, "Input Error", "Desired Salary must be a valid number!");
                return;
            }
        }

        EmploymentApplication application = createApplicationObject(fullName, salary);
        try {
            int result = applicationDao.saveApplication(application);
            if (result > 0) {
                alert(AlertType.INFORMATION, "Success", "Application submitted and saved to database!");
            } else {
                alert(AlertType.ERROR, "Error", "Failed to save application!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            alert(AlertType.ERROR, "Database Error", ex.getMessage());
        }
    }

    public EmploymentApplication createApplicationObject(String fullName, double salary) {
        EmploymentApplication application = new EmploymentApplication();
        application.setFullName(fullName);
        application.setCurrentAddress(txtAddress.getText().trim());
        application.setContactNumber(txtContact.getText().trim());
        application.setEmailAddress(txtEmail.getText().trim());
        application.setHighestEducation(txtEducation.getText().trim());
        application.setGender(getSelectedRadioText(genderGroup));
        application.setDateAvailable(dpDateAvailable.getValue());
        application.setDesiredPosition(txtPosition.getText().trim());
        application.setDesiredSalary(salary);
        application.setLegallyAuthorized(getSelectedRadioText(authGroup));
        application.setHasRelatives(getSelectedRadioText(relGroup));
        application.setRelativesExplanation(txtRelExplain.getText().trim());
        application.setApplicantSignature(txtSignature.getText().trim());
        return application;
    }

    public void alert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void addSectionTitle(GridPane grid, String title, int fontSize, int row) {
        Label label = new Label(title);
        label.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        grid.add(label, 0, row, 2, 1);
    }

    private void addRow(GridPane grid, String labelText, Node inputNode, int row) {
        grid.add(new Label(labelText), 0, row);
        grid.add(inputNode, 1, row);
    }

    private TextField addTextField(GridPane grid, String labelText, int row) {
        TextField field = new TextField();
        addRow(grid, labelText, field, row);
        return field;
    }

    private ToggleGroup addRadioRow(GridPane grid, String labelText, int row, String... labels) {
        ToggleGroup group = new ToggleGroup();
        HBox box = new HBox(15);
        for (String label : labels) {
            RadioButton button = new RadioButton(label);
            button.setToggleGroup(group);
            box.getChildren().add(button);
        }
        addRow(grid, labelText, box, row);
        return group;
    }

    private String getSelectedRadioText(ToggleGroup group) {
        RadioButton selected = (RadioButton) group.getSelectedToggle();
        return (selected != null) ? selected.getText() : StringPool.NOT_SPECIFIED;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
