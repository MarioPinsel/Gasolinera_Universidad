package efm.gasolina.gestor_gasolina.controller.station;

import efm.gasolina.gestor_gasolina.model.station.Station;
import efm.gasolina.gestor_gasolina.repository.station.StationRepository;
import efm.gasolina.gestor_gasolina.service.station.StationAvailability;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/station")
public class StationController {

    @Autowired
    StationRepository stationRepository;

    @Autowired
    StationAvailability stationAvailability; 

    @GetMapping("/all")
    public ResponseEntity<List<Station>> getAll() {
        return ResponseEntity.ok(stationRepository.findAll());
    }

    @GetMapping("/availability/{email}")
    public ResponseEntity<?> getAvailability(@PathVariable String email) {
        try {
            return ResponseEntity.ok(stationAvailability.getAvailability(email));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Operator not found");
        }
    }
}