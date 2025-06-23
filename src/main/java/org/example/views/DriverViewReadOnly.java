package org.example.views;

import org.example.models.entities.Driver;
import org.example.models.repositories.DriverRepository;

import java.util.List;

public class DriverViewReadOnly extends BaseView<Driver> {

    private final DriverRepository repo = DriverRepository.getInstance();

    public DriverViewReadOnly() {
        super("Drivers (Read-Only)", new String[]{
                "ID", "Last Name", "First Name", "Middle Name", "Experience Years"
        }, DriverRepository.getInstance());


        // Отключаем кнопки изменения
        btnCreate.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    @Override
    protected void initForm() {
        // Форма не требуется для read-only
    }

    @Override
    protected void initActions() {
        // Действия не требуются для read-only
    }

    @Override
    protected void fillFormFromTable(int row) {
        // Не нужно ничего делать при клике
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        List<Driver> drivers = repo.getAll();
        for (Driver d : drivers) {
            tableModel.addRow(new Object[]{
                    d.getId(),
                    d.getLastName(),
                    d.getFirstName(),
                    d.getMiddleName(),
                    d.getExperienceYears()
            });
        }
    }
}
