package efm.gasolina.gestor_gasolina.model.wholesaler;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import efm.gasolina.gestor_gasolina.model.sesion.RegisterModel;
import efm.gasolina.gestor_gasolina.model.station.Station;

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

    public Long getId() { return id; }
    public String getVehicle() { return vehicle; }
    public String getConductor() { return conductor; }
    public Integer getVolume() { return volume; }
    public String getFuelType() { return fuelType; }
    public LocalDateTime getDate() { return date; }
    public Station getStation() { return station; }
    public RegisterModel getDistributor() { return distributor; }

    public void setId(Long id) { this.id = id; }
    public void setVehicle(String vehicle) { this.vehicle = vehicle; }
    public void setConductor(String conductor) { this.conductor = conductor; }
    public void setVolume(Integer volume) { this.volume = volume; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public void setStation(Station station) { this.station = station; }
    public void setDistributor(RegisterModel distributor) { this.distributor = distributor; }
}