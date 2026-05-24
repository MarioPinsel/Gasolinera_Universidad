package efm.gasolina.gestor_gasolina.controller.clients;

import efm.gasolina.gestor_gasolina.dto.clients.pqrs.PqrsDTO;
import efm.gasolina.gestor_gasolina.model.clients.Pqrs;
import efm.gasolina.gestor_gasolina.service.Pqrs.PqrsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pqrs")
public class PqrsController {

    private final PqrsService pqrsService;

    public PqrsController(PqrsService pqrsService) {
        this.pqrsService = pqrsService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> enviarPqrs(@RequestBody PqrsDTO dto) {
        try {
            pqrsService.enviarPqrs(dto);
            return ResponseEntity.ok("PQRS enviada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/pending/{brand}")
    public ResponseEntity<?> getPendientes(@PathVariable String brand) {
        try {
            List<Pqrs> lista = pqrsService.getPendientesByBrand(brand);
            if (lista.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/respond")
    public ResponseEntity<?> responder(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            pqrsService.responderPqrs(id, body.get("respuesta"));
            return ResponseEntity.ok("Respuesta enviada");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/responded/{email}")
    public ResponseEntity<?> getRespondidas(@PathVariable String email) {
        try {
            List<Pqrs> lista = pqrsService.getRespondidasByEmail(email);
            if (lista.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}