package efm.gasolina.gestor_gasolina.repository.sale;

import efm.gasolina.gestor_gasolina.model.sale.Sale;
import io.lettuce.core.dynamic.annotation.Param;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByOperatorEmail(String email);

    @Query("SELECT s FROM Sale s WHERE s.station.id = :stationId")
    List<Sale> findByStationId(@Param("stationId") Long stationId);

}