package efm.gasolina.gestor_gasolina.service.opStation;

import efm.gasolina.gestor_gasolina.model.wholesaler.Delivery;
import efm.gasolina.gestor_gasolina.repository.wholesaler.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public void acceptDelivery(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        delivery.setStatus("ACCEPTED");
        deliveryRepository.save(delivery);
    }

    public void rejectDelivery(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        delivery.setStatus("REJECTED");
        deliveryRepository.save(delivery);
    }

    public List<Delivery> getPendingDeliveries(Long stationId) {
        return deliveryRepository.findByStationIdAndStatus(stationId, "PENDING");
    }
}
