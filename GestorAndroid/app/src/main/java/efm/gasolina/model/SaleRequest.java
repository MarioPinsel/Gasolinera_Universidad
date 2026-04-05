package efm.gasolina.model;

public class SaleRequest {
    private String fuelType;
    private String vehicleType;
    private Integer volume;
    private String plate;
    private String operatorEmail;

    public SaleRequest(String fuelType, String vehicleType,
                       Integer volume, String plate,
                       String operatorEmail) {
        this.fuelType = fuelType;
        this.vehicleType = vehicleType;
        this.volume = volume;
        this.plate = plate;
        this.operatorEmail = operatorEmail;
    }

    public String getFuelType() { return fuelType; }
    public String getVehicleType() { return vehicleType; }
    public Integer getVolume() { return volume; }
    public String getPlate() { return plate; }
    public String getOperatorEmail() { return operatorEmail; }
}