package efm.gasolina.gestor_gasolina.model.sesion;

import efm.gasolina.gestor_gasolina.dto.sesion.RegisterDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Operator implements ICredentials{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long id_station;
    private String brand;
    private String zone;
    private String email;
    private String name;
    private String password;
    private String verified;

    public Operator() {
    }

    public Operator(RegisterDTO request) {        
        this.brand = request.brand();
        this.zone = request.zone();
        this.email = request.email();
        this.name = request.name();
        this.password = request.password();
        this.verified = "PENDING";
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_station() {
        return id_station;
    }

    public void setId_station(Long id_station) {
        this.id_station = id_station;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }
}
