package efm.gasolina.gestor_gasolina.model.sesion;

import efm.gasolina.gestor_gasolina.dto.sesion.RegisterDTO;
import jakarta.persistence.*;

@Entity
public class RegisterModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol role;

    public RegisterModel() {
    }

    public RegisterModel(RegisterDTO registerModel) {
    name = registerModel.name();
    email = registerModel.email();
    password = registerModel.password();
    role = registerModel.role();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRole() {
        return role;
    }

    public void setRole(Rol role) {
        this.role = role;
    }
}
