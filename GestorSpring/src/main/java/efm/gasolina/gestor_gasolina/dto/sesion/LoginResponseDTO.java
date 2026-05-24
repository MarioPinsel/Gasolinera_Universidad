package efm.gasolina.gestor_gasolina.dto.sesion;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record LoginResponseDTO(
        @Positive Long id,
        @NotBlank String role,
        @Email String email,
        @Positive Long idStation,
        @NotEmpty String name,
        String brand
) {}