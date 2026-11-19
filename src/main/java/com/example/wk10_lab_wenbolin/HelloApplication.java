package com.example.wk10_lab_wenbolin;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/comp228_wk10_lab";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "070809";

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(12);
        grid.setVgap(10);

        int row = 0;

        Label titleLabel = new Label("John Company - Employment Application");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        grid.add(titleLabel, 0, row++, 2, 1);

        // --- Section 1: Personal Information ---
        Label sec1Label = new Label("Personal Information");
        sec1Label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        grid.add(sec1Label, 0, row++, 2, 1);

        TextField txtFullName = new TextField();
        grid.add(new Label("Full Name:"), 0, row);
        grid.add(txtFullName, 1, row++);

        TextField txtAddress = new TextField();
        grid.add(new Label("Current Address:"), 0, row);
        grid.add(txtAddress, 1, row++);

        TextField txtContact = new TextField();
        grid.add(new Label("Contact Number:"), 0, row);
        grid.add(txtContact, 1, row++);

        TextField txtEmail = new TextField();
        grid.add(new Label("Email Address:"), 0, row);
        grid.add(txtEmail, 1, row++);

        TextField txtEducation = new TextField();
        grid.add(new Label("Highest Educational Attainment:"), 0, row);
        grid.add(txtEducation, 1, row++);

        // Gender
        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton rbMale = new RadioButton("Male");
        RadioButton rbFemale = new RadioButton("Female");
        RadioButton rbOther = new RadioButton("Other");
        rbMale.setToggleGroup(genderGroup);
        rbFemale.setToggleGroup(genderGroup);
        rbOther.setToggleGroup(genderGroup);
        HBox genderBox = new HBox(15, rbMale, rbFemale, rbOther);
        grid.add(new Label("Gender:"), 0, row);
        grid.add(genderBox, 1, row++);

        // --- Section 2: Employment Eligibility ---
        Label sec2Label = new Label("Employment Eligibility");
        sec2Label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        grid.add(sec2Label, 0, row++, 2, 1);

        DatePicker dpDateAvailable = new DatePicker(LocalDate.now());
        grid.add(new Label("Date Available:"), 0, row);
        grid.add(dpDateAvailable, 1, row++);

        TextField txtPosition = new TextField();
        grid.add(new Label("Desired Position:"), 0, row);
        grid.add(txtPosition, 1, row++);

        TextField txtSalary = new TextField();
        txtSalary.setPromptText("e.g. 65000");
        grid.add(new Label("Desired Salary:"), 0, row);
        grid.add(txtSalary, 1, row++);

        // Legally authorized
        ToggleGroup authGroup = new ToggleGroup();
        RadioButton rbAuthYes = new RadioButton("Yes");
        RadioButton rbAuthNo = new RadioButton("No");
        rbAuthYes.setToggleGroup(authGroup);
        rbAuthNo.setToggleGroup(authGroup);
        HBox authBox = new HBox(15, rbAuthYes, rbAuthNo);
        grid.add(new Label("Legally authorized to work in the country?"), 0, row);
        grid.add(authBox, 1, row++);

        // Relatives working
        ToggleGroup relGroup = new ToggleGroup();
        RadioButton rbRelYes = new RadioButton("Yes");
        RadioButton rbRelNo = new RadioButton("No");
        rbRelYes.setToggleGroup(relGroup);
        rbRelNo.setToggleGroup(relGroup);
        HBox relBox = new HBox(15, rbRelYes, rbRelNo);
        grid.add(new Label("Relatives working for our company?"), 0, row);
        grid.add(relBox, 1, row++);

        TextField txtRelExplain = new TextField();
        grid.add(new Label("If yes, please explain further:"), 0, row);
        grid.add(txtRelExplain, 1, row++);

        // --- Signature & Submit ---
        TextField txtSignature = new TextField();
        grid.add(new Label("Applicant's Signature:"), 0, row);
        grid.add(txtSignature, 1, row++);

        Button btnSubmit = new Button("Submit");
        btnSubmit.setPrefWidth(120);
        grid.add(btnSubmit, 1, row);

        // Submit
        btnSubmit.setOnAction(e -> {
            String fullName = txtFullName.getText().trim();
            String address = txtAddress.getText().trim();
            String contact = txtContact.getText().trim();
            String email = txtEmail.getText().trim();
            String education = txtEducation.getText().trim();
            String gender = getSelectedRadioText(genderGroup);
            LocalDate localDate = dpDateAvailable.getValue();
            String position = txtPosition.getText().trim();
            String salaryText = txtSalary.getText().trim();
            String authorized = getSelectedRadioText(authGroup);
            String hasRelatives = getSelectedRadioText(relGroup);
            String relExplain = txtRelExplain.getText().trim();
            String signature = txtSignature.getText().trim();

            if (fullName.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Full Name cannot be empty!");
                return;
            }

            double salary = 0.0;
            if (!salaryText.isEmpty()) {
                try {
                    salary = Double.parseDouble(salaryText);
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Desired Salary must be a valid number!");
                    return;
                }
            }

            String sql = "INSERT INTO employment_applications "
                    + "(full_name, current_address, contact_number, email_address, highest_education, "
                    + "gender, date_available, desired_position, desired_salary, legally_authorized, "
                    + "has_relatives, relatives_explanation, applicant_signature) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, fullName);
                pstmt.setString(2, address);
                pstmt.setString(3, contact);
                pstmt.setString(4, email);
                pstmt.setString(5, education);
                pstmt.setString(6, gender);
                pstmt.setDate(7, localDate != null ? Date.valueOf(localDate) : null);
                pstmt.setString(8, position);
                pstmt.setDouble(9, salary);
                pstmt.setString(10, authorized);
                pstmt.setString(11, hasRelatives);
                pstmt.setString(12, relExplain);
                pstmt.setString(13, signature);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Application submitted and saved to database!");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
            }
        });

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 600, 650);
        primaryStage.setTitle("Employment Application - Wk10 Lab");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String getSelectedRadioText(ToggleGroup group) {
        RadioButton selected = (RadioButton) group.getSelectedToggle();
        return (selected != null) ? selected.getText() : "Not Specified";
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}