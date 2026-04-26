package ui;

import dao.StudentDAO;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentUI extends JFrame {

    JTextField nameField, courseField, emailField;
    JButton addBtn, updateBtn, deleteBtn, refreshBtn;

    JTable table;
    DefaultTableModel model;

    StudentDAO dao = new StudentDAO();
    int selectedId = -1;

    public StudentUI() {
        setTitle("Student Management System");
        setSize(700, 500);
        setLayout(null);

        // Labels
        addLabel("Name:", 20, 20);
        addLabel("Course:", 20, 60);
        addLabel("Email:", 20, 100);

        // Fields
        nameField = addTextField(100, 20);
        courseField = addTextField(100, 60);
        emailField = addTextField(100, 100);

        // Buttons
        addBtn = addButton("Add", 20, 150);
        updateBtn = addButton("Update", 120, 150);
        deleteBtn = addButton("Delete", 240, 150);
        refreshBtn = addButton("Refresh", 360, 150);

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Name", "Course", "Email"}, 0);
        table = new JTable(model);

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 200, 640, 230);
        add(sp);

        loadTable();

        // EVENTS 🔥

        // Add
        addBtn.addActionListener(e -> {
            dao.addStudent(new Student(
                    nameField.getText(),
                    courseField.getText(),
                    emailField.getText()
            ));
            loadTable();
            clearFields();
        });

        // Table Click (SELECT)
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                selectedId = Integer.parseInt(model.getValueAt(row, 0).toString());

                nameField.setText(model.getValueAt(row, 1).toString());
                courseField.setText(model.getValueAt(row, 2).toString());
                emailField.setText(model.getValueAt(row, 3).toString());
            }
        });

        // Update
        updateBtn.addActionListener(e -> {
            if (selectedId != -1) {
                dao.updateStudent(
                        selectedId,
                        nameField.getText(),
                        courseField.getText(),
                        emailField.getText()
                );
                loadTable();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Select a student first!");
            }
        });

        // Delete
        deleteBtn.addActionListener(e -> {
            if (selectedId != -1) {
                dao.deleteStudent(selectedId);
                loadTable();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Select a student first!");
            }
        });

        // Refresh
        refreshBtn.addActionListener(e -> loadTable());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // 🔄 Load data into table
    void loadTable() {
        model.setRowCount(0);
        ArrayList<Student> list = dao.getAllStudents();

        for (Student s : list) {
            model.addRow(new Object[]{
                    s.getId(),
                    s.getName(),
                    s.getCourse(),
                    s.getEmail()
            });
        }
    }

    void clearFields() {
        nameField.setText("");
        courseField.setText("");
        emailField.setText("");
        selectedId = -1;
    }

    // Helper Methods (clean code)
    JLabel addLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 80, 25);
        add(label);
        return label;
    }

    JTextField addTextField(int x, int y) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 200, 25);
        add(tf);
        return tf;
    }

    JButton addButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 100, 30);
        add(btn);
        return btn;
    }
}