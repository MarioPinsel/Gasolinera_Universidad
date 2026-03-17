package efm.gasolina.gestor_gasolina.controller.wholesaler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import efm.gasolina.gestor_gasolina.dto.wholesaler.DeliveryDTO;
import efm.gasolina.gestor_gasolina.handler.ExceedsCapacityException;
import efm.gasolina.gestor_gasolina.model.wholesaler.Delivery;
import efm.gasolina.gestor_gasolina.service.wholesaler.DeliveryService;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody DeliveryDTO request) {
        try {
            Delivery delivery = deliveryService.registerDelivery(request);
            return ResponseEntity.ok(delivery);
        } catch (ExceedsCapacityException e) {
            return ResponseEntity.status(409).body("EXCEEDS_CAPACITY");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/history/{email}")
    public ResponseEntity<List<Delivery>> getHistory(@PathVariable String email) {
        return ResponseEntity.ok(deliveryService.getDeliveriesByDistributor(email));
    }
}