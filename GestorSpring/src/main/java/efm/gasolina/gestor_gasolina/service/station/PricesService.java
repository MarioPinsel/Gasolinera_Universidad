package efm.gasolina.gestor_gasolina.service.station;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import efm.gasolina.gestor_gasolina.dto.station.StationRequestDTO;
import efm.gasolina.gestor_gasolina.repository.station.StationRepository;

@Service
public class PricesService {
    private final StationRepository stationRepository;

    public PricesService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public ResponseEntity<List<StationRequestDTO>> getPricesAndFranchise(String zone, String type) {        
        List<Object[]> response = stationRepository.findByZoneAndType(zone, type);

        if (!response.isEmpty()) {
            List<StationRequestDTO> dtos = response.stream()
                    .map(row -> new StationRequestDTO(
                            (String) row[0], (Integer) row[1]))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
