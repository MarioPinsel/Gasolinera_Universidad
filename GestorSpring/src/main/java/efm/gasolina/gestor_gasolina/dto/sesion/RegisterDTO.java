package efm.gasolina.gestor_gasolina.dto.sesion;

import com.fasterxml.jackson.annotation.JsonAlias;
import efm.gasolina.gestor_gasolina.model.sesion.Rol;
public record RegisterDTO(
        @JsonAlias("name") String name,
        @JsonAlias("email") String email,
        @JsonAlias("password") String password,
        @JsonAlias("role") Rol role
        )
{}
