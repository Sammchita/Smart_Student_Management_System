package dao;

import db.DBConnection;
import model.Student;

import java.sql.*;
import java.util.ArrayList;

public class StudentDAO {

    public void addStudent(Student student) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO students(name, course, email) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, student.getName());
            ps.setString(2, student.getCourse());
            ps.setString(3, student.getEmail());

            ps.executeUpdate();
            System.out.println("Student Added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM students";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("course"),
                        rs.getString("email")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateStudent(int id, String name, String course, String email) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "UPDATE students SET name=?, course=?, email=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, course);
            ps.setString(3, email);
            ps.setInt(4, id);

            ps.executeUpdate();
            System.out.println("Student Updated!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "DELETE FROM students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Student Deleted!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}