package efm.gasolina.gestor_gasolina.model.sesion;

import efm.gasolina.gestor_gasolina.dto.sesion.RegisterDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
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

    private String verified;

    public RegisterModel() {
    }

    public RegisterModel(RegisterDTO registerModel) {
    name = registerModel.name();
    email = registerModel.email();
    password = registerModel.password();
    role = registerModel.role();
    verified = "PENDING";
    }
}
