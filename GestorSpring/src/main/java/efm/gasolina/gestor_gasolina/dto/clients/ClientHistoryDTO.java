package efm.gasolina.gestor_gasolina.dto.clients;

import java.time.LocalDateTime;

public record ClientHistoryDTO(
        String brand,
        String zone,
        String fuelType,
        String vehicleType,
        Integer volume,
        Integer totalPrice,
        LocalDateTime date
) {}