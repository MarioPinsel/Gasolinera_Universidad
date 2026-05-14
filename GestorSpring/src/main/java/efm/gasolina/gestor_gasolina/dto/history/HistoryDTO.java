package efm.gasolina.gestor_gasolina.dto.history;

import java.time.LocalDateTime;

public record HistoryDTO(
        String tipo,
        String placa,
        Integer volumen,
        Integer total,
        LocalDateTime fecha
) {}