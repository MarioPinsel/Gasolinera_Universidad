package efm.gasolina.gestor_gasolina.repository.station;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import efm.gasolina.gestor_gasolina.model.station.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT v.regular_subsidy FROM Vehicle v WHERE v.vehicle_type = ?1")
    Optional<Integer> findRegularSubsidyByVehicle(String vehicle_type);

    @Query("SELECT v.diesel_subsidy FROM Vehicle v WHERE v.vehicle_type = ?1")
    Optional<Integer> findDieselSubsidyByVehicle(String vehicle_type);

    @Query("SELECT v.vehicle_type FROM Vehicle v")
    List<String> findAllVehicleTypes();
}
