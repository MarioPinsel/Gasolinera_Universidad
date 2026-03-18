package efm.gasolina.gestor_gasolina.model.station;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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

    public Station() {}

    public Station(String type, String zone, String franchise, Integer price, Integer quantity) {
        this.type = type;
        this.zone = zone;
        this.franchise = franchise;
        this.price_difference = price;
        this.quantity = quantity;
    }
}
