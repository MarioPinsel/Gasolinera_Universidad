package efm.gasolina.gestor_gasolina.model.station;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String zone;
    private String franchise;
    private Integer price_difference;
    private Integer quantity;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getFranchise() {
            return franchise;
        }

        public void setFranchise(String franchise) {
            this.franchise = franchise;
        }

        public Integer getPrice_difference() {
            return price_difference;
        }

        public void setPrice_difference(Integer price_difference) {
            this.price_difference = price_difference;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

    public Station() {}

    public Station(String type, String zone, String franchise, Integer price, Integer quantity) {
        this.type = type;
        this.zone = zone;
        this.franchise = franchise;
        this.price_difference = price;
        this.quantity = quantity;
    }
}
