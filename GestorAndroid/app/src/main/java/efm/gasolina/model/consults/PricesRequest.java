package efm.gasolina.model.consults;

public class PricesRequest {
    private String franchise;
    private String price;

    public PricesRequest() {
    }

    public PricesRequest(String franchise, String price) {
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
