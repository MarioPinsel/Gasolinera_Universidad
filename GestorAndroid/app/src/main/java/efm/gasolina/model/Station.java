package efm.gasolina.model;

public class Station {
    private Long id;
    private String type;
    private String zone;
    private String franchise;
    private Integer price_difference;
    private Integer quantity;

    public Long getId() { return id; }
    public String getType() { return type; }
    public String getZone() { return zone; }
    public String getFranchise() { return franchise; }
    public Integer getPrice_difference() { return price_difference; }
    public Integer getQuantity() { return quantity; }

    public void setId(Long id) { this.id = id; }
    public void setType(String type) { this.type = type; }
    public void setZone(String zone) { this.zone = zone; }
    public void setFranchise(String franchise) { this.franchise = franchise; }
    public void setPrice_difference(Integer price_difference) { this.price_difference = price_difference; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
