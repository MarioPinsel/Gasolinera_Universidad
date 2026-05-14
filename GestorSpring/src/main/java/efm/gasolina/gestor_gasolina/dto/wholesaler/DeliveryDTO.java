package efm.gasolina.gestor_gasolina.dto.wholesaler;

import jakarta.validation.constraints.*;

public record DeliveryDTO(
    @NotBlank String vehicle,
    @NotEmpty String conductor,
    @Positive Integer volume,
    @NotEmpty String fuelType,
    @Positive Integer price,
    @PositiveOrZero Long stationId,
    @Email String distributorEmail
) {}
