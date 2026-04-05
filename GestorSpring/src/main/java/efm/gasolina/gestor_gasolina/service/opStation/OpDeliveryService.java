package efm.gasolina.gestor_gasolina.service.opStation;

import efm.gasolina.gestor_gasolina.handler.ExceedsCapacityException;
import efm.gasolina.gestor_gasolina.model.station.Station;
import efm.gasolina.gestor_gasolina.model.wholesaler.Delivery;
import efm.gasolina.gestor_gasolina.repository.station.StationRepository;
import efm.gasolina.gestor_gasolina.repository.wholesaler.DeliveryRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OpDeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final StationRepository stationRepository;

    public OpDeliveryService(DeliveryRepository deliveryRepository, StationRepository stationRepository) {
        this.deliveryRepository = deliveryRepository;
        this.stationRepository = stationRepository;
    }

     public void acceptDelivery(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        Station station = delivery.getStation();
        Integer volume = delivery.getVolume();
        String fuelType = delivery.getFuelType();

        if ("Corriente".equals(fuelType)) {
            if (volume > station.getRegular_capacity() ||
                    station.getRegular_quantity() + volume > station.getRegular_capacity()) {
                throw new ExceedsCapacityException();
            }
            station.setRegular_quantity(station.getRegular_quantity() + volume);
        }

        else if ("Diesel".equals(fuelType)) {
            if (volume > station.getDiesel_capacity() ||
                    station.getDiesel_quantity() + volume > station.getDiesel_capacity()) {
                throw new ExceedsCapacityException();
            }
            station.setDiesel_quantity(station.getDiesel_quantity() + volume);
        }
        else {
            throw new IllegalArgumentException("Tipo de combustible no válido: " + fuelType);
        }
    
        delivery.setStatus("ACCEPTED");
        stationRepository.save(station);
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