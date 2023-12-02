package ru.esstu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public abstract class StudentListTest {

    protected abstract StudentList createStudentList();

    @Test
    void testAddAndGetAll() {
        StudentList studentList = createStudentList();

        Student student1 = new Student("1", "Иван", "Иванов", "", "Инф101");
        Student student2 = new Student("2", "Мария", "Сидорова", "", "Инф102");
        Student student3 = new Student("3", "Петр", "Петров", "", "Инф103");

        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);

        assertEquals(3, studentList.getAll().size());
        assertTrue(studentList.getAll().contains(student1));
        assertTrue(studentList.getAll().contains(student2));
        assertTrue(studentList.getAll().contains(student3));
    }

    @Test
    void testGetById() {
        StudentList studentList = createStudentList();

        Student student1 = new Student("1", "Иван", "Иванов", "", "Инф101");
        Student student2 = new Student("2", "Мария", "Сидорова", "", "Инф102");

        studentList.add(student1);
        studentList.add(student2);

        assertEquals(student1, studentList.getById("1"));
        assertEquals(student2, studentList.getById("2"));
        assertNull(studentList.getById("3"));
    }

    @Test
    void testUpdate() {
        StudentList studentList = createStudentList();

        Student student1 = new Student("1", "Иван", "Иванов", "", "Инф101");
        Student student2 = new Student("1", "Мария", "Сидорова", "", "Инф102");

        studentList.add(student1);
        studentList.update(student2);

        assertEquals(student2, studentList.getById("1"));
    }

    @Test
    void testDelete() {
        StudentList studentList = createStudentList();

        Student student1 = new Student("1", "Иван", "Иванов", "", "Инф101");

        studentList.add(student1);
        studentList.delete("1");

        assertEquals(0, studentList.getAll().size());
        assertNull(studentList.getById("1"));
    }

    @Test
    void testAddAndUpdate() {
        StudentList studentList = createStudentList();

        Student student1 = new Student("1", "Иван", "Иванов", "", "Инф101");
        Student student2 = new Student("1", "Мария", "Сидорова", "", "Инф102");

        studentList.add(student1);
        studentList.update(student2);

        assertEquals(1, studentList.getAll().size());
        assertEquals(student2, studentList.getById("1"));
    }

    @Test
    void testAddAndDelete() {
        StudentList studentList = createStudentList();

        Student student1 = new Student("1", "Иван", "Иванов", "", "Инф101");


        studentList.add(student1);
        studentList.delete("1");

        assertEquals(0, studentList.getAll().size());
        assertNull(studentList.getById("1"));
    }
}
