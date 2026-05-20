package efm.gasolina.model.clients;

public class ClientSaleRequest {
    private String email;
    private String brand;
    private String zone;
    private String fuelType;
    private String vehicleType;
    private Integer volume;

    public ClientSaleRequest(String email, String brand, String zone,
                             String fuelType, String vehicleType, Integer volume) {
        this.email       = email;
        this.brand       = brand;
        this.zone        = zone;
        this.fuelType    = fuelType;
        this.vehicleType = vehicleType;
        this.volume      = volume;
    }

    public String getEmail()       { return email; }
    public String getBrand()       { return brand; }
    public String getZone()        { return zone; }
    public String getFuelType()    { return fuelType; }
    public String getVehicleType() { return vehicleType; }
    public Integer getVolume()     { return volume; }
}