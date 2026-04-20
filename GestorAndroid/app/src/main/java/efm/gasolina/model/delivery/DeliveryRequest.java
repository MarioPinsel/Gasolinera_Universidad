package efm.gasolina.model.delivery;

public class DeliveryRequest {
    private String vehicle;
    private String conductor;
    private Integer volume;
    private String fuelType;
    private Integer price;
    private Long stationId;
    private String distributorEmail;

    public DeliveryRequest(String vehicle, String conductor, Integer volume,
                           String fuelType, Integer price, Long stationId, String distributorEmail) {
        this.vehicle = vehicle;
        this.conductor = conductor;
        this.volume = volume;
        this.fuelType = fuelType;
        this.price = price;
        this.stationId = stationId;
        this.distributorEmail = distributorEmail;
    }

    public String getVehicle() { return vehicle; }
    public String getConductor() { return conductor; }
    public Integer getVolume() { return volume; }
    public String getFuelType() { return fuelType; }
    public Integer getPrice() { return price; }
    public Long getStationId() { return stationId; }
    public String getDistributorEmail() { return distributorEmail; }
}