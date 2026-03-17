package efm.gasolina.gestor_gasolina.controller.station;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import efm.gasolina.gestor_gasolina.model.station.Station;
import efm.gasolina.gestor_gasolina.repository.station.StationRepository;

@RestController
@RequestMapping("/station")
public class StationController {

    @Autowired
    StationRepository stationRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Station>> getAll() {
        return ResponseEntity.ok(stationRepository.findAll());
    }
}
