package efm.gasolina.gestor_gasolina.dto.clients;

public record ClientSaleDTO(
        String email,
        String brand,
        String zone,
        String fuelType,
        String vehicleType,
        Integer volume
) {}