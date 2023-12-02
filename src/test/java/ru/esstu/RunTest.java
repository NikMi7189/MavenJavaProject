package ru.esstu;

public class RunTest extends StudentListTest {
    @Override
    protected StudentList createStudentList() {
        return new StudentListFile("fdsf");
    }
}
