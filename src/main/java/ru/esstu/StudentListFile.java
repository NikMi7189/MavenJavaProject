package ru.esstu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentListFile implements StudentList {
    private String directory;
    private String format = ".txt";
    private List<Student> students;
    private final String logFileName = "logFile";

    public StudentListFile(String directoryName) {
        this.directory = System.getProperty("user.dir") + File.separator + directoryName;
        this.students = new ArrayList<>();
        File directoryFile = new File(this.directory);

        if (!directoryFile.exists()) {
            directoryFile.mkdirs();
        }
    }

    @Override
    public List<Student> getAll() {
        loadFromFiles();
        return students;
    }

    @Override
    public void add(Student student) {
        loadFromFiles();
        students.add(student);
        saveToFile(student);
    }

    @Override
    public Student getById(String id) {
        loadFromFiles();
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    @Override
    public void delete(String id) {
        loadFromFiles();
        Student studentToRemove = null;
        for (Student student : students) {
            if (student.getId().equals(id)) {
                studentToRemove = student;
                break;
            }
        }
        if (studentToRemove != null) {
            students.remove(studentToRemove);
            deleteFile(id);
        }
    }

    @Override
    public void update(Student student) {
        loadFromFiles();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(student.getId())) {
                students.set(i, student);
                saveToFile(student);
                break;
            }
        }
    }

    private void loadFromFiles() {
        students.clear();
        File folder = new File(directory);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                Student student = loadFromFile(file);
                if (student != null) {
                    students.add(student);
                }
            }
        }
    }

    private Student loadFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String id = null;
            String firstName = null;
            String lastName = null;
            String partonymicName = null;
            String group = null;

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID:")) {
                    id = line.substring(3).trim();
                } else if (line.startsWith("First Name:")) {
                    firstName = line.substring(11).trim();
                } else if (line.startsWith("Last Name:")) {
                    lastName = line.substring(10).trim();
                } else if (line.startsWith("Partonymic Name:")) {
                    partonymicName = line.substring(17).trim();
                } else if (line.startsWith("Group:")) {
                    group = line.substring(7).trim();
                }
            }

            if (id != null && firstName != null && lastName != null && partonymicName != null && group != null) {
                return new Student(id, firstName, lastName, partonymicName, group);
            }
        } catch (IOException e) {
            ErrorHandler.handleException(e, "Ошибка при загрузке данных из файла");
        }

        return null;
    }

    private void saveToFile(Student student) {
        String fileName = directory + File.separator + student.getId() + format;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("ID: " + student.getId());
            writer.newLine();
            writer.write("First Name: " + student.getFirstName());
            writer.newLine();
            writer.write("Last Name: " + student.getLastName());
            writer.newLine();
            writer.write("Partonymic Name: " + student.getPartonymicName());
            writer.newLine();
            writer.write("Group: " + student.getGroup());
        } catch (IOException e) {
            ErrorHandler.handleException(e, "Ошибка при сохранении данных в файл");
        }
    }

    private void deleteFile(String id) {
        String fileName = directory + File.separator + id + format;
        File file = new File(fileName);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Запись была успешно удалена");
            } else {
                System.out.println("Произошла ошибка при удалении записи");
            }
        }
    }
}