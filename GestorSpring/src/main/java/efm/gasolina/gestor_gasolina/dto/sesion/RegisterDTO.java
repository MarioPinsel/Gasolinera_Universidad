package efm.gasolina.gestor_gasolina.dto.sesion;

import com.fasterxml.jackson.annotation.JsonAlias;
import efm.gasolina.gestor_gasolina.model.sesion.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
        @NotEmpty @JsonAlias("name") String name,
        @Email @JsonAlias("email") String email,
        @NotEmpty @JsonAlias("password") String password,
        @NotNull @JsonAlias("role") Rol role,
        @JsonAlias("brand") String brand,
        @JsonAlias("zone") String zone
)
{}