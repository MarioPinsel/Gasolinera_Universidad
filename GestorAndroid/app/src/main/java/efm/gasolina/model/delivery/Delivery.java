package efm.gasolina.model.delivery;

import efm.gasolina.model.sation.Station;
import efm.gasolina.model.auth.User;

public class Delivery {
    private Long id;
    private String vehicle;
    private String conductor;
    private Integer volume;
    private String fuelType;
    private Integer price;
    private String date;
    private Station station;
    private User distributor;

    private String status;

    public Long getId() { return id; }
    public String getVehicle() { return vehicle; }
    public String getConductor() { return conductor; }
    public Integer getVolume() { return volume; }
    public String getFuelType() { return fuelType; }
    public String getDate() { return date; }
    public Station getStation() { return station; }
    public User getDistributor() { return distributor; }

    public void setId(Long id) { this.id = id; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public void setVehicle(String vehicle) { this.vehicle = vehicle; }
    public void setConductor(String conductor) { this.conductor = conductor; }
    public void setVolume(Integer volume) { this.volume = volume; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    public void setDate(String date) { this.date = date; }
    public void setStation(Station station) { this.station = station; }
    public void setDistributor(User distributor) { this.distributor = distributor; }
}
