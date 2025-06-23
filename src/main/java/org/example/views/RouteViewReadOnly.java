package org.example.views;

import org.example.models.entities.Route;
import org.example.models.repositories.RouteRepository;

public class RouteViewReadOnly extends BaseView<Route> {

    public RouteViewReadOnly() {
        super("Routes (Read-Only)",
                new String[]{"ID", "Route Name", "Estimated Distance", "Company ID"},
                RouteRepository.getInstance());

        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Отключаем кнопки создания, обновления и удаления — только просмотр
        btnCreate.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        // Если у BaseView есть форма, скрываем её, чтобы не редактировать данные
        if (formPanel != null) {
            formPanel.setVisible(false);
        }

        // Загружаем данные сразу
        loadData();
    }

    @Override
    protected void initForm() {
        // В режиме read-only форма не нужна, можно оставить пустым
    }

    @Override
    protected void fillFormFromTable(int row) {
        // Не используется в read-only режиме
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        for (Route r : repository.getAll()) {
            tableModel.addRow(new Object[]{
                    r.getId(),
                    r.getName(),
                    r.getEstimatedDistance(),
                    r.getCompanyId()
            });
        }
    }

    @Override
    protected void initActions() {
        // Можно оставить пустым или, например, добавить кнопку обновления, если она есть
        btnRefresh.addActionListener(e -> loadData());
    }
}
