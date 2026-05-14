package efm.gasolina.gestor_gasolina.dto.sesion;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @Email String email,
        @NotBlank String password
)
{}