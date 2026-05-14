package efm.gasolina.gestor_gasolina.dto.legal;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record DecreesDTO(
        @NotEmpty String name,
        @NotEmpty String typeOfGas,
        @Positive Integer value
) {}