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

        setTitle("logistics++ - " + title);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        // Стили для таблицы
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(26);
        table.setSelectionBackground(new Color(230, 242, 255));
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(176, 224, 230));
        header.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Форма ввода
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Форма для ввода"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        formPanel.setBackground(Color.WHITE);

        // Кнопки
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(Color.WHITE);

        btnCreate = createButton("Создать", new Color(176, 224, 230));
        btnUpdate = createButton("Изменить", new Color(176, 224, 230));
        btnDelete = createButton("Удалить", new Color(255, 99, 71));
        btnRefresh = createButton("Обновить", new Color(176, 224, 230));

        Font btnFont = new Font("Segoe UI", Font.BOLD, 14);
        Dimension btnSize = new Dimension(110, 36);
        for (JButton btn : new JButton[]{btnCreate, btnUpdate, btnDelete, btnRefresh}) {
            btn.setFont(btnFont);
            btn.setPreferredSize(btnSize);
            buttonPanel.add(btn);
        }

        // Основная компоновка
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

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

    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return btn;
    }

    protected void addFormField(String label, JComponent field) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lbl);
        formPanel.add(field);
    }

    protected abstract void initForm();
    protected abstract void fillFormFromTable(int row);
    protected abstract void loadData();
    protected abstract void initActions();
}