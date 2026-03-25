package efm.gasolina.gestor_gasolina.controller.opStation;

import efm.gasolina.gestor_gasolina.model.wholesaler.Delivery;

import efm.gasolina.gestor_gasolina.service.opStation.OpDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeliveryStatusController {

    @Autowired
    private OpDeliveryService opDeliveryService;

    @Transactional
    @PutMapping("/{id}/accept")
    public ResponseEntity<String> acceptDelivery(@PathVariable Long id) {
        try {
            opDeliveryService.acceptDelivery(id);
            return ResponseEntity.ok("Entrega aceptada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Transactional
    @PutMapping("/{id}/reject")
    public ResponseEntity<String> rejectDelivery(@PathVariable Long id) {
        try {
            opDeliveryService.rejectDelivery(id);
            return ResponseEntity.ok("Entrega rechazada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/station/{stationId}/pending")
    public ResponseEntity<List<Delivery>> getPendingDeliveries(@PathVariable Long stationId) {
        try {
            List<Delivery> deliveries = opDeliveryService.getPendingDeliveries(stationId);
            if (deliveries.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(deliveries);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
