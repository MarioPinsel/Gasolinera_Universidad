package efm.gasolina.gestor_gasolina.dto.wholesaler;

public record DeliveryDTO(
    String vehicle,
    String conductor,
    Integer volume,
    String fuelType,
    Long stationId,
    String distributorEmail
) {}
