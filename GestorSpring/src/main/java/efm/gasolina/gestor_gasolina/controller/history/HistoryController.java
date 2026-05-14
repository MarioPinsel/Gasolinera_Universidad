package efm.gasolina.gestor_gasolina.controller.history;

import efm.gasolina.gestor_gasolina.dto.history.HistoryDTO;
import efm.gasolina.gestor_gasolina.service.history.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/historial")
public class HistoryController {

    private final HistoryService historialService;

    public HistoryController(HistoryService historialService) {
        this.historialService = historialService;
    }

    @GetMapping("/operador/{email}/{stationId}")
    public ResponseEntity<List<HistoryDTO>> obtenerHistorial(
            @PathVariable String email,
            @PathVariable Long stationId) {
        try {
            List<HistoryDTO> historial = historialService.obtenerHistorial(email, stationId);
            if (historial.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(historial);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}