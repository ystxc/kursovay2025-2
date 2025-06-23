package org.example.controllers;

import models.entities.Company;
import models.repositories.CompanyRepository;

import java.util.List;

public class CompanyController {
    private CompanyRepository repository;

    public CompanyController() {
        this.repository = CompanyRepository.getInstance();
    }

    public List<Company> getAllCompanies() {
        return repository.getAll();
    }

    public void addCompany(Company company) {
        repository.create(company);
    }

    public void updateCompany(Company company) {
        repository.update(company);
    }

    public void deleteCompany(int companyId) {
        repository.delete(companyId);
    }
}
