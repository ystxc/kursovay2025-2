package models.entities;

import java.math.BigDecimal;

// Таблица routes
public class Route {
    private int id;
    private String name;
    private BigDecimal estimatedDistance;
    private BigDecimal basePayment;
    private int companyId;

    public Route(int id, String name, BigDecimal estimatedDistance, BigDecimal basePayment, int companyId) {
        this.id = id;
        this.name = name;
        this.estimatedDistance = estimatedDistance;
        this.basePayment = basePayment;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getEstimatedDistance() {
        return estimatedDistance;
    }

    public void setEstimatedDistance(BigDecimal estimatedDistance) {
        this.estimatedDistance = estimatedDistance;
    }

    public BigDecimal getBasePayment() {
        return basePayment;
    }

    public void setBasePayment(BigDecimal basePayment) {
        this.basePayment = basePayment;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}