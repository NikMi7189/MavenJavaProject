package ru.esstu;

import java.util.ArrayList;
import java.util.List;

public class StudentListArrayList implements StudentList {
    private List<Student> students;

    public StudentListArrayList() {
        this.students = new ArrayList<>();
    }

    @Override
    public List<Student> getAll() {
        return students;
    }

    @Override
    public void add(Student student) {
        try {
            students.add(student);
        } catch (Exception e) {
            ErrorHandler.handleException(e, "Ошибка при добавлении студента в список");
        }
    }

    @Override
    public Student getById(String id) {
        try {
            for (Student student : students) {
                if (student.getId().equals(id)) {
                    return student;
                }
            }
        } catch (Exception e) {
            ErrorHandler.handleException(e, "Ошибка при получении студента по ID из списка");
        }
        return null;
    }

    @Override
    public void delete(String id) {
        try {
            Student studentToRemove = null;
            for (Student student : students) {
                if (student.getId().equals(id)) {
                    studentToRemove = student;
                    break;
                }
            }
            if (studentToRemove != null) {
                students.remove(studentToRemove);
            }
        } catch (Exception e) {
            ErrorHandler.handleException(e, "Ошибка при удалении студента из списка");
        }
    }

    @Override
    public void update(Student student) {
        try {
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId().equals(student.getId())) {
                    students.set(i, student);
                    break;
                }
            }
        } catch (Exception e) {
            ErrorHandler.handleException(e, "Ошибка при обновлении студента в списке");
        }
    }
}