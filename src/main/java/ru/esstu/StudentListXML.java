package ru.esstu;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentListXML implements StudentList {
    private List<Student> students;
    private String xmlFileName;
    private final String logFileName = "logXML";

    public StudentListXML(String xmlFileName) {
        this.xmlFileName = xmlFileName + ".xml";
        this.students = new ArrayList<>();
        loadFromXml();
    }

    @Override
    public List<Student> getAll() {
        return students;
    }

    @Override
    public void add(Student student) {
        students.add(student);
        saveToXml();
    }

    @Override
    public Student getById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    @Override
    public void delete(String id) {
        Student studentToRemove = null;
        for (Student student : students) {
            if (student.getId().equals(id)) {
                studentToRemove = student;
                break;
            }
        }
        if (studentToRemove != null) {
            students.remove(studentToRemove);
            saveToXml();
        }
    }

    @Override
    public void update(Student student) {
        for (Student existingStudent : students) {
            if (existingStudent.getId().equals(student.getId())) {
                existingStudent.setFirstName(student.getFirstName());
                existingStudent.setLastName(student.getLastName());
                existingStudent.setPartonymicName(student.getPartonymicName());
                existingStudent.setGroup(student.getGroup());
                saveToXml();
                return;
            }
        }
    }

    private void loadFromXml() {
        try {
            XStream xstream = new XStream(new StaxDriver());
            xstream.alias("students", List.class);
            xstream.alias("student", Student.class);

            File file = new File(xmlFileName);
            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    students = (List<Student>) xstream.fromXML(fis);
                }
            }
        } catch (Exception e) {
            ErrorHandler.handleException(e, "Ошибка при загрузке данных из XML");
        }
    }

    private void saveToXml() {
        try {
            XStream xstream = new XStream(new DomDriver());
            xstream.alias("students", List.class);
            xstream.alias("student", Student.class);

            try (FileOutputStream fos = new FileOutputStream(xmlFileName)) {
                xstream.toXML(students, fos);
            }
        } catch (Exception e) {
            ErrorHandler.handleException(e, "Ошибка при сохранении данных в XML");
        }
    }
}
