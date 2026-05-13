package efm.gasolina.gestor_gasolina.dto.sale;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record SaleDTO(
    @NotEmpty String fuelType,
    @NotEmpty String vehicleType,
    @Positive Integer volume,
    @NotBlank String plate,
    @Email String operatorEmail
) {}
