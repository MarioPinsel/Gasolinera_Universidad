package efm.gasolina.model.sation;

public class Station {
    private Long id;
    private String type;
    private String zone;
    private String brand;
    private Integer price_difference;
    private Integer quantity;

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
}
