package efm.gasolina.model.clients;

public class ClientHistoryDTO {
    private String brand;
    private String zone;
    private String fuelType;
    private String vehicleType;
    private Integer volume;
    private Integer totalPrice;
    private String date;

    public String getBrand()       { return brand; }
    public String getZone()        { return zone; }
    public String getFuelType()    { return fuelType; }
    public String getVehicleType() { return vehicleType; }
    public Integer getVolume()     { return volume; }
    public Integer getTotalPrice() { return totalPrice; }
    public String getDate()        { return date; }
}