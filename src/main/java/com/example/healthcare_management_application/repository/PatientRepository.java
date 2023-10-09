package com.example.healthcare_management_application.repository;

import com.example.healthcare_management_application.model.Patient;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PatientRepository {
    private String DATA_SOURCE_URL = "jdbc:mysql://127.0.0.1:3306/";
    private final String DB_CREATION_ST = "CREATE DATABASE IF NOT EXISTS patient_records;";
    private final String TABLE_CREATION_ST = "CREATE TABLE IF NOT EXISTS patients (" +
            "ID INT AUTO_INCREMENT," +
            "first_name VARCHAR(255)," +
            "last_name VARCHAR(255)," +
            "date_of_birth VARCHAR(255)," +
            "gender VARCHAR(255)," +
            "PRIMARY KEY (ID)" +
            ");";
    private final String INSERT_SQL = "INSERT INTO patients (first_name, last_name, date_of_birth, gender) VALUES (?, ?, ?, ?)";
    private final String GET_LAST = "SELECT * FROM patients ORDER BY ID DESC LIMIT 1;";
    private final String LIST_SQL = "SELECT * FROM patients;";
    private final String GET_PATIENT_BY_ID = "SELECT * FROM patients WHERE ID = ?;";
    private final String FIND_PATIENT_BY_NAME = "SELECT * FROM patients " +
            "WHERE CONCAT(first_name, ' ', last_name) LIKE '%?%';";

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
            createPatientsTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createPatientsTable() {
        try (Connection connection = dataSource().getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement(TABLE_CREATION_ST);
            statement2.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Patient> findPatients(String key) {
        List<Patient> list = new ArrayList<>();
        try (Connection connection = dataSource().getConnection()) {
            PreparedStatement st = connection.prepareStatement(FIND_PATIENT_BY_NAME);
            ResultSet set = st.executeQuery();
            while (set.next()) {
                Patient patient = new Patient();
                patient.setId(Long.parseLong(set.getString("id")));
                patient.setFirstName(set.getString("first_name"));
                patient.setLastName(set.getString("last_name"));
                patient.setDateOfBirth(set.getString("date_of_birth"));
                patient.setGender(set.getString("gender"));
                list.add(patient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public long savePatient(Patient patient) {
        try (Connection connection = dataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_SQL);
            statement.setString(1, patient.getFirstName());
            statement.setString(2, patient.getLastName());
            statement.setString(3, patient.getDateOfBirth());
            statement.setString(4, patient.getGender());
            statement.executeUpdate();
            return getLastAddedPatient().getId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Patient getLastAddedPatient() {
        try (Connection connection = dataSource().getConnection()) {
            PreparedStatement st = connection.prepareStatement(GET_LAST);
            ResultSet set = st.executeQuery();
            Patient patient = new Patient();
            while (set.next()) {
                patient.setId(Long.parseLong(set.getString("id")));
                patient.setFirstName(set.getString("first_name"));
                patient.setLastName(set.getString("last_name"));
                patient.setDateOfBirth(set.getString("date_of_birth"));
                patient.setGender(set.getString("gender"));
            }
            return patient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Patient> getPatients() {
        List<Patient> list = new ArrayList<>();
        try (Connection connection = dataSource().getConnection()) {
            PreparedStatement st = connection.prepareStatement(LIST_SQL);
            ResultSet set = st.executeQuery();
            while (set.next()) {
                Patient patient = new Patient();
                patient.setId(Long.parseLong(set.getString("id")));
                patient.setFirstName(set.getString("first_name"));
                patient.setLastName(set.getString("last_name"));
                patient.setDateOfBirth(set.getString("date_of_birth"));
                patient.setGender(set.getString("gender"));
                list.add(patient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Patient getPatientById(Long id) {
        try (Connection connection = dataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_PATIENT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Patient patient = new Patient();
                patient.setId(resultSet.getLong("ID"));
                patient.setFirstName(resultSet.getString("first_name"));
                patient.setDateOfBirth(resultSet.getString("date_of_birth"));
                patient.setGender(resultSet.getString("gender"));
                patient.setLastName(resultSet.getString("last_name"));
                return patient;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Patient with the given ID not found
    }
}

