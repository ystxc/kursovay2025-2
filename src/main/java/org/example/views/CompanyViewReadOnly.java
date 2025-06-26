package org.example.views;

import org.example.models.entities.Company;
import org.example.models.repositories.CompanyRepository;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class CompanyViewReadOnly extends BaseView<Company> {

    public CompanyViewReadOnly() {
        super("logistics++ - Компании (просмотр)", new String[]{
                "ID", "Название компании"
        }, CompanyRepository.getInstance());

        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        // Настройка таблицы
        table.setSelectionBackground(new Color(230, 242, 255));
        table.setGridColor(new Color(220, 220, 220));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);

        // заголовок таблицы
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(176, 224, 230));
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Ненужные элементы
        btnCreate.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        btnRefresh.setVisible(false);
        formPanel.setVisible(false);

        // Новая компоновка
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Заголовок
        JLabel titleLabel = new JLabel("Список компаний (режим просмотра)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Таблица
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // подпись о режиме просмотра
        JLabel modeLabel = new JLabel("Режим просмотра - редактирование запрещено", SwingConstants.CENTER);
        modeLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        modeLabel.setForeground(Color.GRAY);
        modeLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        mainPanel.add(modeLabel, BorderLayout.SOUTH);

        // новая компоновка
        setContentPane(mainPanel);

        loadData();
    }

    @Override
    protected void initForm() {
    }

    @Override
    protected void initActions() {
    }

    @Override
    protected void fillFormFromTable(int row) {
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        List<Company> companies = repository.getAll();
        for (Company c : companies) {
            tableModel.addRow(new Object[]{
                    c.getId(),
                    c.getName()
            });
        }
    }
}