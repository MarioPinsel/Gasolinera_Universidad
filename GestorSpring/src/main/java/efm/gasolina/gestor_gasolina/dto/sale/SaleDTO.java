package efm.gasolina.gestor_gasolina.dto.sale;

public record SaleDTO(
    String fuelType,
    String vehicleType,
    Integer volume,
    String plate,          
    String operatorEmail
) {}
