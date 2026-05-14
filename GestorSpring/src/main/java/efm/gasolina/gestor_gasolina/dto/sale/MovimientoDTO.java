package efm.gasolina.gestor_gasolina.dto.sale;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record MovimientoDTO(
        @NotEmpty String tipo,
        @NotEmpty String placa,
        @Positive Integer volumen,
        @Positive Integer total,
        @PastOrPresent LocalDateTime fecha
) {}