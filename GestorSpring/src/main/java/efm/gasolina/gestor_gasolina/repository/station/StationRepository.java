package efm.gasolina.gestor_gasolina.repository.station;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import efm.gasolina.gestor_gasolina.model.station.Station;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Long>{
        
    @Query("SELECT s.brand, s.regular_price_difference FROM Station s WHERE s.zone = ?1 ORDER BY s.regular_price_difference ASC")
    List<Object[]> findBrandAndRegularDiff(String zone);

    @Query("SELECT s.brand, s.diesel_price_difference FROM Station s WHERE s.zone = ?1  ORDER BY s.diesel_price_difference ASC")
    List<Object[]> findBrandAndDieselDiff(String zone);

    @Query("SELECT s.id FROM Station s WHERE s.brand = :brand AND s.zone = :zone")
    Long findIdByBrandAndZone(@Param("brand") String brand, @Param("zone") String zone);
}
