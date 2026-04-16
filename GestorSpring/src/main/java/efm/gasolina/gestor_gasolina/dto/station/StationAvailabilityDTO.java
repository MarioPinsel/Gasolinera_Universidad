package efm.gasolina.gestor_gasolina.dto.station;

public class StationAvailabilityDTO {
    private String zone;
    private String brand;
    private Integer dieselQuantity;
    private Integer dieselCapacity;
    private Integer regularQuantity;
    private Integer regularCapacity;

    public StationAvailabilityDTO(String zone, String brand,
                                   Integer dieselQuantity, Integer dieselCapacity,
                                   Integer regularQuantity, Integer regularCapacity) {
        this.zone = zone;
        this.brand = brand;
        this.dieselQuantity = dieselQuantity;
        this.dieselCapacity = dieselCapacity;
        this.regularQuantity = regularQuantity;
        this.regularCapacity = regularCapacity;
    }

    public String getZone() { return zone; }
    public String getBrand() { return brand; }
    public Integer getDieselQuantity() { return dieselQuantity; }
    public Integer getDieselCapacity() { return dieselCapacity; }
    public Integer getRegularQuantity() { return regularQuantity; }
    public Integer getRegularCapacity() { return regularCapacity; }
}