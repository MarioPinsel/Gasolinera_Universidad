package efm.gasolina.gestor_gasolina.controller.sale;

import efm.gasolina.gestor_gasolina.dto.sale.SaleDTO;
import efm.gasolina.gestor_gasolina.model.sale.Sale;
import efm.gasolina.gestor_gasolina.service.sale.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    SaleService saleService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SaleDTO request) {
        try {
            Sale sale = saleService.registerSale(request);
            return ResponseEntity.ok(sale);
        } catch (RuntimeException e) {
            return switch (e.getMessage()) {
                case "OPERATOR_NOT_FOUND" -> ResponseEntity.status(404).body("Operator not found");
                case "STATION_NOT_FOUND" -> ResponseEntity.status(404).body("Station not found");
                case "INSUFFICIENT_FUEL" -> ResponseEntity.status(409).body("Insufficient fuel");
                case "INVALID_FUEL_TYPE" -> ResponseEntity.status(400).body("Invalid fuel type");
                default -> ResponseEntity.status(500).body("Server error");
            };
        }
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<String>> getVehicleTypes() {
        return ResponseEntity.ok(saleService.getVehicleTypes());
    }

    @GetMapping("/price/{email}/{fuelType}/{vehicleType}")
    public ResponseEntity<Integer> getPrice(
            @PathVariable String email,
            @PathVariable String fuelType,
            @PathVariable String vehicleType) {
        try {
            return ResponseEntity.ok(saleService.calculatePrice(email, fuelType, vehicleType));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }
}