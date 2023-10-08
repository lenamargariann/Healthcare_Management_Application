package com.example.healthcare_management_application.repository;
import com.example.healthcare_management_application.model.Appointment;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class AppointmentRepository {
    private String DATA_SOURCE_URL = "jdbc:mysql://127.0.0.1:3306/";
    private final String DB_CREATION_ST = "CREATE DATABASE IF NOT EXISTS patient_records;";
    private final String TABLE_CREATION_ST = "CREATE TABLE IF NOT EXISTS appointments (" +
            "ID INT AUTO_INCREMENT," +
            "patientId INT," +
            "date VARCHAR(255)," +
            "doctor VARCHAR(255)," +
            "PRIMARY KEY (ID)" +
            ");";
    private static final String INSERT_SQL = "INSERT INTO appointments (patientId, date, doctor) VALUES (?, ?, ?)";
    private final String LIST_APPOINTMENTS_SQL = "SELECT * FROM appointments WHERE patientId = ?";

    {
        createDatabase();
    }
private DataSource dataSource;

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
            createAppointmentsTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createAppointmentsTable() {
        try (Connection connection = dataSource().getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement(TABLE_CREATION_ST);
            statement2.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean saveAppointment(Appointment appointment) {
        try (Connection connection = dataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_SQL);
            statement.setLong(1, appointment.getPatientId());
            statement.setString(2, appointment.getDate());
            statement.setString(3, appointment.getDoctor());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        List<Appointment> list = new ArrayList<>();
        try (Connection connection = dataSource().getConnection()) {
            PreparedStatement st = connection.prepareStatement(LIST_APPOINTMENTS_SQL);
            st.setLong(1, patientId);
            ResultSet set = st.executeQuery();
            while (set.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(Long.parseLong(set.getString("ID")));
                appointment.setPatientId(patientId);
                appointment.setDate(set.getString("date"));
                appointment.setDoctor(set.getString("doctor"));
                list.add(appointment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}

