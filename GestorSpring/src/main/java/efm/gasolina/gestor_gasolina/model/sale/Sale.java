package efm.gasolina.gestor_gasolina.model.sale;

import efm.gasolina.gestor_gasolina.model.sesion.Operator;
import efm.gasolina.gestor_gasolina.model.station.Station;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fuelType;
    private String vehicleType;
    private Integer volume;
    private Integer pricePerGallon;
    private Integer totalPrice;
    private String plate;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private Operator operator;

    public Sale() {
    }

    public Sale(String fuelType, String vehicleType, Integer volume,
            Integer pricePerGallon, String plate,
            Station station, Operator operator) {
        this.fuelType = fuelType;
        this.vehicleType = vehicleType;
        this.volume = volume;
        this.pricePerGallon = pricePerGallon;
        this.totalPrice = pricePerGallon * volume;
        this.plate = plate; 
        this.station = station;
        this.operator = operator;
        this.date = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public Integer getVolume() {
        return volume;
    }

    public Integer getPricePerGallon() {
        return pricePerGallon;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Station getStation() {
        return station;
    }

    public Operator getOperator() {
        return operator;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public void setPricePerGallon(Integer pricePerGallon) {
        this.pricePerGallon = pricePerGallon;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}