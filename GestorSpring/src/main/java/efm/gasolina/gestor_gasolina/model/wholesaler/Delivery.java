package efm.gasolina.gestor_gasolina.model.wholesaler;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import efm.gasolina.gestor_gasolina.model.sesion.RegisterModel;
import efm.gasolina.gestor_gasolina.model.station.Station;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicle;
    private String conductor;
    private Integer volume;
    private String fuelType;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "distributor_id")
    private RegisterModel distributor;  

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;  

    public Delivery() {}

    public Delivery(String vehicle, String conductor, Integer volume,
                    String fuelType, Station station,
                    RegisterModel distributor) {
        this.vehicle = vehicle;
        this.conductor = conductor;
        this.volume = volume;
        this.fuelType = fuelType;
        this.station = station;
        this.distributor = distributor;
        this.date = LocalDateTime.now();
    }
}