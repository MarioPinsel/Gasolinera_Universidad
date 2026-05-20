package efm.gasolina.model.sation;

public class Station {
    private Long id;
    private String type;
    private String zone;
    private String brand;
    private Integer price_difference;
    private Integer quantity;
    private Double latitude;
    private Double longitude;

    public Long getId() { return id; }
    public String getType() { return type; }
    public String getZone() { return zone; }
    public String getBrand() { return brand; }
    public Integer getPrice_difference() { return price_difference; }
    public Integer getQuantity() { return quantity; }

    public void setId(Long id) { this.id = id; }
    public void setType(String type) { this.type = type; }
    public void setZone(String zone) { this.zone = zone; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setPrice_difference(Integer price_difference) { this.price_difference = price_difference; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public void setLatitude(Double lat) { this.latitude = lat; }
    public void setLongitude(Double lng) { this.longitude = lng; }
}
