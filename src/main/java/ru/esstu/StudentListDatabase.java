package ru.esstu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentListDatabase implements StudentList {
    private final String jdbcUrl;
    private final String username;
    private final String password;
    private Connection connection;

    public StudentListDatabase(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        this.connection = createConnection();
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Не удалось установить соединение с БД");
        }
    }

    @Override
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM student")) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String partonymicName = resultSet.getString("partonymic_name");
                String group = resultSet.getString("grooup");

                students.add(new Student(id, firstName, lastName, partonymicName, group));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void add(Student student) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO student (id, first_name, last_name, partonymic_name, grooup) VALUES (?, ?, ?, ?, ?)")) {

            statement.setString(1, student.getId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setString(4, student.getPartonymicName());
            statement.setString(5, student.getGroup());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student getById(String id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM student WHERE id = ?")) {

            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String partonymicName = resultSet.getString("partonymic_name");
                String group = resultSet.getString("grooup");

                return new Student(id, firstName, lastName, partonymicName, group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(String id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM student WHERE id = ?")) {

            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Student student) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE student SET first_name = ?, last_name = ?, partonymic_name = ?, grooup = ? WHERE id = ?")) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setString(3, student.getPartonymicName());
            statement.setString(4, student.getGroup());
            statement.setString(5, student.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}