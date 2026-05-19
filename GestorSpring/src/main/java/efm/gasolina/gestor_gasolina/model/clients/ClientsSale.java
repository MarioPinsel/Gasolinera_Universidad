package efm.gasolina.gestor_gasolina.model.clients;

import efm.gasolina.gestor_gasolina.dto.clients.ClientSaleDTO;
import efm.gasolina.gestor_gasolina.model.sesion.Clients;
import efm.gasolina.gestor_gasolina.model.station.Station;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ClientsSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Clients client;

    private String brand;
    private String zone;
    private String fuelType;
    private String vehicleType;
    private Integer volume;
    private Integer totalPrice;
    private LocalDateTime date;

    public ClientsSale() {}

    public ClientsSale(Clients client, Station station,
                       String fuelType, String vehicleType,
                       Integer volume, Integer totalPrice) {
        this.client      = client;
        this.brand       = station.getBrand();
        this.zone        = station.getZone();
        this.fuelType    = fuelType;
        this.vehicleType = vehicleType;
        this.volume      = volume;
        this.totalPrice  = totalPrice;
        this.date        = LocalDateTime.now();
    }

    public Long getId()             { return id; }
    public Clients getClient()      { return client; }
    public String getBrand()        { return brand; }
    public String getZone()         { return zone; }
    public String getFuelType()     { return fuelType; }
    public String getVehicleType()  { return vehicleType; }
    public Integer getVolume()      { return volume; }
    public Integer getTotalPrice()  { return totalPrice; }
    public LocalDateTime getDate()  { return date; }
}
