package models.entities;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;


// Таблица route_drivers
public class RouteDriver {
    private int routeId;
    private int driverId;
    private Date departureDate;
    private Date arrivalDate;
    private boolean bonusStatus;
    private BigDecimal actualPayment;

    public RouteDriver(int routeId, int driverId, LocalDate departureDate, LocalDate arrivalDate,
                       boolean bonusStatus, BigDecimal actualPayment) {
        this.routeId = routeId;
        this.driverId = driverId;
        this.departureDate = Date.valueOf(departureDate);
        this.arrivalDate = Date.valueOf(arrivalDate);
        this.bonusStatus = bonusStatus;
        this.actualPayment = actualPayment;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public boolean isBonusStatus() {
        return bonusStatus;
    }

    public void setBonusStatus(boolean bonusStatus) {
        this.bonusStatus = bonusStatus;
    }

    public BigDecimal getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(BigDecimal actualPayment) {
        this.actualPayment = actualPayment;
    }
}
