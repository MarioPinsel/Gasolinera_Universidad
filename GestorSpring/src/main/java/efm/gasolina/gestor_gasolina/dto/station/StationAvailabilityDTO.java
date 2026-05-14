package efm.gasolina.gestor_gasolina.dto.station;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record StationAvailabilityDTO(
        @NotEmpty String zone,
        @NotEmpty String brand,
        @Positive Integer dieselQuantity,
        @Positive Integer dieselCapacity,
        @Positive Integer regularQuantity,
        @Positive Integer regularCapacity
) {}