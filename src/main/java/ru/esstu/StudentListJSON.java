package ru.esstu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentListJSON implements StudentList {
    private final String jsonFileName;

    public StudentListJSON(String jsonFileName) {
        this.jsonFileName = jsonFileName + ".json";
    }

    @Override
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        try {
            JSONArray jsonArray = readJSONArrayFromJSONFile();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonStudent = jsonArray.getJSONObject(i);
                Student student = jsonToStudent(jsonStudent);
                students.add(student);
            }
        } catch (IOException e) {
            ErrorHandler.handleException(e, "Ошибка при чтении списка студентов из JSON");
        }
        return students;
    }

    @Override
    public void add(Student student) {
        try {
            JSONArray jsonArray = readJSONArrayFromJSONFile();
            JSONObject jsonStudent = studentToJSON(student);
            jsonArray.put(jsonStudent);
            writeJSONArrayToJSONFile(jsonArray);
        } catch (IOException e) {
            ErrorHandler.handleException(e, "Ошибка при добавлении студента в JSON");
        }
    }

    @Override
    public Student getById(String id) {
        try {
            JSONArray jsonArray = readJSONArrayFromJSONFile();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonStudent = jsonArray.getJSONObject(i);
                if (jsonStudent.getString("id").equals(id)) {
                    return jsonToStudent(jsonStudent);
                }
            }
        } catch (IOException e) {
            ErrorHandler.handleException(e, "Ошибка при получении студента из JSON по ID");
        }
        return null;
    }

    @Override
    public void delete(String id) {
        try {
            JSONArray jsonArray = readJSONArrayFromJSONFile();
            JSONArray newJsonArray = new JSONArray();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonStudent = jsonArray.getJSONObject(i);
                if (!jsonStudent.getString("id").equals(id)) {
                    newJsonArray.put(jsonStudent);
                }
            }
            writeJSONArrayToJSONFile(newJsonArray);
        } catch (IOException e) {
            ErrorHandler.handleException(e, "Ошибка при удалении студента из JSON");
        }
    }

    @Override
    public void update(Student student) {
        try {
            JSONArray jsonArray = readJSONArrayFromJSONFile();
            JSONArray newJsonArray = new JSONArray();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonStudent = jsonArray.getJSONObject(i);
                if (jsonStudent.getString("id").equals(student.getId())) {
                    JSONObject updatedJsonStudent = studentToJSON(student);
                    newJsonArray.put(updatedJsonStudent);
                } else {
                    newJsonArray.put(jsonStudent);
                }
            }
            writeJSONArrayToJSONFile(newJsonArray);
        } catch (IOException e) {
            ErrorHandler.handleException(e, "Ошибка при обновлении студента в JSON");
        }
    }

    private JSONArray readJSONArrayFromJSONFile() throws IOException {
        File file = new File(jsonFileName);
        if (!file.exists()) {
            return new JSONArray();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            return new JSONArray(json.toString());
        }
    }

    private void writeJSONArrayToJSONFile(JSONArray jsonArray) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFileName))) {
            writer.write(jsonArray.toString(2));
        }
    }

    private JSONObject studentToJSON(Student student) {
        JSONObject jsonStudent = new JSONObject();
        jsonStudent.put("id", student.getId());
        jsonStudent.put("firstName", student.getFirstName());
        jsonStudent.put("lastName", student.getLastName());
        jsonStudent.put("partonymicName", student.getPartonymicName());
        jsonStudent.put("group", student.getGroup());
        return jsonStudent;
    }

    private Student jsonToStudent(JSONObject jsonStudent) {
        String id = jsonStudent.getString("id");
        String firstName = jsonStudent.getString("firstName");
        String lastName = jsonStudent.getString("lastName");
        String partonymicName = jsonStudent.getString("partonymicName");
        String group = jsonStudent.getString("group");
        return new Student(id, firstName, lastName, partonymicName, group);
    }
}
