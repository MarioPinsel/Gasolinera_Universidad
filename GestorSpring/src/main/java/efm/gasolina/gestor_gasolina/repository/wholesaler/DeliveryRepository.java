package efm.gasolina.gestor_gasolina.repository.wholesaler;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import efm.gasolina.gestor_gasolina.model.sesion.RegisterModel;
import efm.gasolina.gestor_gasolina.model.wholesaler.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByDistributor(RegisterModel distributor);

    List<Delivery> findByStationIdAndStatus(Long stationId, String status);

}
