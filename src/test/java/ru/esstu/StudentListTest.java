package ru.esstu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public abstract class StudentListTest {

    protected abstract StudentList createStudentList();

    @Test
    void testAddAndGetAll() {
        StudentList studentList = createStudentList();

        Student student1 = new Student("1", "John", "Doe", "", "CS101");
        Student student2 = new Student("2", "Jane", "Doe", "", "CS102");

        studentList.add(student1);
        studentList.add(student2);

        assertEquals(2, studentList.getAll().size());
        assertTrue(studentList.getAll().contains(student1));
        assertTrue(studentList.getAll().contains(student2));
    }

    @Test
    void testGetById() {
        StudentList studentList = createStudentList();

        Student student1 = new Student("1", "John", "Doe", "", "CS101");
        Student student2 = new Student("2", "Jane", "Doe", "", "CS102");

        studentList.add(student1);
        studentList.add(student2);

        assertEquals(student1, studentList.getById("1"));
        assertEquals(student2, studentList.getById("2"));
        assertNull(studentList.getById("3"));
    }

    @Test
    void testUpdate() {
        StudentList studentList = createStudentList();

        Student student1 = new Student("1", "John", "Doe", "", "CS101");
        Student student2 = new Student("1", "Jane", "Doe", "", "CS102"); // Same ID as student1

        studentList.add(student1);
        studentList.update(student2);

        assertEquals(student2, studentList.getById("1"));
    }

    @Test
    void testDelete() {
        StudentList studentList = createStudentList();

        Student student1 = new Student("1", "John", "Doe", "", "CS101");

        studentList.add(student1);
        studentList.delete("1");

        assertEquals(0, studentList.getAll().size());
        assertNull(studentList.getById("1"));
    }
}
