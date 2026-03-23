package efm.gasolina.gestor_gasolina.model.station;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicle_type;
    private Integer regular_subsidy;
    private Integer diesel_subsidy;
    

    public Vehicle() {
    }

    public Vehicle(String vehicle, Integer diesel_subsidy, Integer regular_subsidy) {
        this.vehicle_type = vehicle;
        this.diesel_subsidy = diesel_subsidy;
        this.regular_subsidy = regular_subsidy;
    }

    public Long getId() {
        return id;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public Integer getDiesel_subsidy() {
        return diesel_subsidy;
    }

    public Integer getRegular_subsidy() {
        return regular_subsidy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVehicle_type(String vehicle) {
        this.vehicle_type = vehicle;
    }

    public void setDiesel_subsidy(Integer diesel_subsidy) {
        this.diesel_subsidy = diesel_subsidy;
    }

    public void setRegular_subsidy(Integer regular_subsidy) {
        this.regular_subsidy = regular_subsidy;
    }

}