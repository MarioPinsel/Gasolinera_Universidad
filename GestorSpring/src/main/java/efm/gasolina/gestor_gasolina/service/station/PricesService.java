package efm.gasolina.gestor_gasolina.service.station;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import efm.gasolina.gestor_gasolina.dto.station.StationRequestDTO;
import efm.gasolina.gestor_gasolina.repository.station.StationRepository;
import efm.gasolina.gestor_gasolina.repository.station.VehicleRepository;

@Service
public class PricesService {
    private final StationRepository stationRepository;
    private final VehicleRepository vehicleRepository;
    private Integer dieselBase;
    private Integer regularBase;

    public PricesService(StationRepository stationRepository, VehicleRepository vehicleRepository) {
        this.stationRepository = stationRepository;
        this.vehicleRepository = vehicleRepository;
        this.dieselBase = 11300;
        this.regularBase = 16500;
    }

    public ResponseEntity<List<StationRequestDTO>> getPricesAndFranchise(String zone, String type, String vehicleType) {
        List<Object[]> stationInfo = null;
        final Integer finalPriceDiff;

        if (type.equals("Corriente")) {
            stationInfo = stationRepository.findBrandAndRegularDiff(zone);
            finalPriceDiff = regularBase - vehicleRepository.findRegularSubsidyByVehicle(vehicleType).orElse(0);
        } else if (type.equals("Diesel")) {

            stationInfo = stationRepository.findBrandAndDieselDiff(zone);
            finalPriceDiff = dieselBase - vehicleRepository.findDieselSubsidyByVehicle(vehicleType).orElse(0);
        } else {
            return ResponseEntity.notFound().build();
        }

        if (stationInfo != null && !stationInfo.isEmpty()) {
            List<StationRequestDTO> dtos = stationInfo.stream()
                    .map(station -> new StationRequestDTO(
                            (String) station[0],
                            (Integer) station[1] + finalPriceDiff))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
