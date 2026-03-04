package efm.gasolina.gestor_gasolina.dto.station;

public class StationRequestDTO {
    private String franchise;
    private String price;

    public StationRequestDTO() {
    }

    public StationRequestDTO(String franchise, String price) {
        this.franchise = franchise;
        this.price = price;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
