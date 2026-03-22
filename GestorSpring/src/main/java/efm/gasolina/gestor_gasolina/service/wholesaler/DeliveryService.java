package efm.gasolina.gestor_gasolina.service.wholesaler;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import efm.gasolina.gestor_gasolina.dto.wholesaler.DeliveryDTO;
import efm.gasolina.gestor_gasolina.handler.ExceedsCapacityException;
import efm.gasolina.gestor_gasolina.model.sesion.RegisterModel;
import efm.gasolina.gestor_gasolina.model.station.Station;
import efm.gasolina.gestor_gasolina.model.wholesaler.Delivery;
import efm.gasolina.gestor_gasolina.repository.sesion.SesionRepository;
import efm.gasolina.gestor_gasolina.repository.station.StationRepository;
import efm.gasolina.gestor_gasolina.repository.wholesaler.DeliveryRepository;

@Service

public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private SesionRepository sesionRepository;

    public Delivery registerDelivery(DeliveryDTO request) {

        Station station = stationRepository.findById(request.stationId())
                .orElseThrow(() -> new RuntimeException("STATION_NOT_FOUND"));

        RegisterModel distributor = sesionRepository.findByEmail(request.distributorEmail())
                .orElseThrow(() -> new RuntimeException("DISTRIBUTOR_NOT_FOUND"));

        if (request.volume() > station.getQuantity())
            throw new ExceedsCapacityException();

        Delivery delivery = new Delivery(
                request.vehicle(),
                request.conductor(),
                request.volume(),
                request.fuelType(),
                station,
                distributor);

        return deliveryRepository.save(delivery);
    }

    public List<Delivery> getDeliveriesByDistributor(String email) {
        RegisterModel distributor = sesionRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("DISTRIBUTOR_NOT_FOUND"));
        return deliveryRepository.findByDistributor(distributor);
    }

    
}
