package efm.gasolina.gestor_gasolina.dto.error;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ErrorDTO(
    @Positive Integer code,
    @NotNull String message,
    String error
) {}
