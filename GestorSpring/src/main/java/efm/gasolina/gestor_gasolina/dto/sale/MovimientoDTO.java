package efm.gasolina.gestor_gasolina.dto.sale;

import java.time.LocalDateTime;

public record MovimientoDTO(
        String tipo,
        String placa,
        Integer volumen,
        Integer total,
        LocalDateTime fecha
) {}