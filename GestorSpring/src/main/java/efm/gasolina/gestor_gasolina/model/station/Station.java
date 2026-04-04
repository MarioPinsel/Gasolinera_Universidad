package efm.gasolina.gestor_gasolina.model.station;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zone;
    private String brand;
    private Integer diesel_quantity;
    private Integer diesel_capacity;    
    private Integer regular_quantity;
    private Integer regular_capacity;
    private Integer diesel_price_difference;
    private Integer regular_price_difference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String franchise) {
        this.brand = franchise;
    }

    public Integer getDiesel_quantity() {
        return diesel_quantity;
    }

    public void setDiesel_quantity(Integer diesel_cuantity) {
        this.diesel_quantity = diesel_cuantity;
    }

    public Integer getRegular_quantity() {
        return regular_quantity;
    }

    public void setRegular_quantity(Integer regular_cuantity) {
        this.regular_quantity = regular_cuantity;
    }

    public Integer getDiesel_price_difference() {
        return diesel_price_difference;
    }

    public void setDiesel_price_difference(Integer diesel_price_difference) {
        this.diesel_price_difference = diesel_price_difference;
    }

    public Integer getRegular_price_difference() {
        return regular_price_difference;
    }

    public void setRegular_price_difference(Integer regular_price_difference) {
        this.regular_price_difference = regular_price_difference;
    }

    public Integer getDiesel_capacity() {
        return diesel_capacity;
    }

    public void setDiesel_capacity(Integer diesel_capacity) {
        this.diesel_capacity = diesel_capacity;
    }

    public Integer getRegular_capacity() {
        return regular_capacity;
    }

    public void setRegular_capacity(Integer regular_capacity) {
        this.regular_capacity = regular_capacity;
    }

}
