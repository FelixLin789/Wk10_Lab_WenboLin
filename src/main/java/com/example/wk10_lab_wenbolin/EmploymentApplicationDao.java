package com.example.wk10_lab_wenbolin;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class EmploymentApplicationDao {
    public int saveApplication(EmploymentApplication application) throws SQLException {
        String query = "INSERT INTO employment_applications "
                + "(full_name, current_address, contact_number, email_address, highest_education, "
                + "gender, date_available, desired_position, desired_salary, legally_authorized, "
                + "has_relatives, relatives_explanation, applicant_signature) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.getDBConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                int counter = 1;
                statement.setString(counter++, application.getFullName());
                statement.setString(counter++, application.getCurrentAddress());
                statement.setString(counter++, application.getContactNumber());
                statement.setString(counter++, application.getEmailAddress());
                statement.setString(counter++, application.getHighestEducation());
                statement.setString(counter++, application.getGender());
                LocalDate dateAvailable = application.getDateAvailable();
                statement.setDate(counter++, dateAvailable != null ? Date.valueOf(dateAvailable) : null);
                statement.setString(counter++, application.getDesiredPosition());
                statement.setDouble(counter++, application.getDesiredSalary());
                statement.setString(counter++, application.getLegallyAuthorized());
                statement.setString(counter++, application.getHasRelatives());
                statement.setString(counter++, application.getRelativesExplanation());
                statement.setString(counter++, application.getApplicantSignature());

                int rowsInserted = statement.executeUpdate();
                connection.commit();

                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                }
                return rowsInserted;
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    ex.addSuppressed(rollbackEx);
                }
                throw ex;
            }
        }
    }
}
