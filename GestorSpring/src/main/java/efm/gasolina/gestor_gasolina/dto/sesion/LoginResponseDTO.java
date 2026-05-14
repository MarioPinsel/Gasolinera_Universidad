package efm.gasolina.gestor_gasolina.dto.sesion;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record LoginResponseDTO(
        @NotBlank String role,
        @Email String email,
        @Positive Long idStation
) {}
