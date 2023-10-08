package com.example.healthcare_management_application.repository;

import com.example.healthcare_management_application.model.Prescription;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PrescriptionRepository {
    private String DATA_SOURCE_URL = "jdbc:mysql://127.0.0.1:3306/";
    private final String DB_CREATION_ST = "CREATE DATABASE IF NOT EXISTS patient_records;";
    private final String TABLE_CREATION_ST = "CREATE TABLE IF NOT EXISTS prescriptions (" +
            "ID INT AUTO_INCREMENT," +
            "patientId INT," +
            "name VARCHAR(255)," +
            "daysCount INT," +
            "PRIMARY KEY (ID)" +
            ");";
    private static final String INSERT_SQL = "INSERT INTO prescriptions (patientId, name, daysCount) VALUES (?, ?, ?)";
    private final String LIST_PRESCRIPTIONS_SQL = "SELECT * FROM prescriptions WHERE patientId = ?";

    {
        createDatabase();
    }

    private DataSource dataSource() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(DATA_SOURCE_URL);
        dataSource.setUsername("lenamargariann");
        dataSource.setPassword("Dinozavr-123");
        return dataSource;
    }

    private void createDatabase() {
        try (Connection connection = dataSource().getConnection()) {
            PreparedStatement statement1 = connection.prepareStatement(DB_CREATION_ST);
            statement1.execute();
            DATA_SOURCE_URL += "patient_records";
            createPrescriptionsTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createPrescriptionsTable() {
        try (Connection connection = dataSource().getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement(TABLE_CREATION_ST);
            statement2.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean savePrescription(Prescription prescription) {
        try (Connection connection = dataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_SQL);
            statement.setLong(1, prescription.getPatientId());
            statement.setString(2, prescription.getName());
            statement.setInt(3, prescription.getDaysCount());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Prescription> getPrescriptionsByPatientId(Long patientId) {
        List<Prescription> list = new ArrayList<>();
        try (Connection connection = dataSource().getConnection()) {
            PreparedStatement st = connection.prepareStatement(LIST_PRESCRIPTIONS_SQL);
            st.setLong(1, patientId);
            ResultSet set = st.executeQuery();
            while (set.next()) {
                Prescription prescription = new Prescription();
                prescription.setPrescriptionId(Long.parseLong(set.getString("ID")));
                prescription.setPatientId(patientId);
                prescription.setName(set.getString("name"));
                prescription.setDaysCount(set.getInt("daysCount"));
                list.add(prescription);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}

