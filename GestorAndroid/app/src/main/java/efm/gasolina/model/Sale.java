package efm.gasolina.model;

public class Sale {
    private Long id;
    private String fuelType;
    private String vehicleType;
    private Integer volume;
    private Integer pricePerGallon;
    private Integer totalPrice;
    private String date;
    private Station station;
    private User operator;

    public Long getId() { return id; }
    public String getFuelType() { return fuelType; }
    public String getVehicleType() { return vehicleType; }
    public Integer getVolume() { return volume; }
    public Integer getPricePerGallon() { return pricePerGallon; }
    public Integer getTotalPrice() { return totalPrice; }
    public String getDate() { return date; }
    public Station getStation() { return station; }
    public User getOperator() { return operator; }

    public void setId(Long id) { this.id = id; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public void setVolume(Integer volume) { this.volume = volume; }
    public void setPricePerGallon(Integer pricePerGallon) { this.pricePerGallon = pricePerGallon; }
    public void setTotalPrice(Integer totalPrice) { this.totalPrice = totalPrice; }
    public void setDate(String date) { this.date = date; }
    public void setStation(Station station) { this.station = station; }
    public void setOperator(User operator) { this.operator = operator; }
}