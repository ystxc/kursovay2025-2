package org.example.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.example.models.repositories.Repository;

public abstract class BaseView<T> extends JFrame {
    protected Repository<T> repository;
    protected DefaultTableModel tableModel;
    protected JTable table;
    protected JButton btnCreate, btnUpdate, btnDelete, btnRefresh;
    protected JPanel formPanel;

    public BaseView(String title, String[] columnNames, Repository<T> repository) {
        this.repository = repository;

        setTitle(title);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(26);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JScrollPane scrollPane = new JScrollPane(table);

        // Форма ввода
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Кнопки
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        btnCreate = new JButton("Create");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnRefresh = new JButton("Refresh");

        Font btnFont = new Font("Segoe UI", Font.BOLD, 14);
        Dimension btnSize = new Dimension(110, 36);
        for (JButton btn : new JButton[]{btnCreate, btnUpdate, btnDelete, btnRefresh}) {
            btn.setFont(btnFont);
            btn.setPreferredSize(btnSize);
            buttonPanel.add(btn);
        }

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadData());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    fillFormFromTable(row);
                }
            }
        });

        initForm();
        initActions();
        loadData();
    }

    protected void addFormField(String label, JComponent field) {
        formPanel.add(new JLabel(label));
        formPanel.add(field);
    }

    protected abstract void initForm();                 // Создание формы ввода (сверху)
    protected abstract void fillFormFromTable(int row); // Заполнение полей при выборе строки
    protected abstract void loadData();                  // Загрузка данных из БД
    protected abstract void initActions();               // Навешивание событий на кнопки
}
