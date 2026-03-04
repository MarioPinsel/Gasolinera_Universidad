package efm.gasolina.gestor_gasolina.repository.station;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import efm.gasolina.gestor_gasolina.dto.station.StationRequestDTO;
import efm.gasolina.gestor_gasolina.model.station.Stations;
import java.util.List;


public interface StationRepository extends JpaRepository<Stations, Long>{
        
    @Query("SELECT s.franchise, s.price FROM Station s WHERE s.zone = ?1 AND s.type = ?2")
    List<StationRequestDTO> findByZoneAndType(String zone, String type);
}
