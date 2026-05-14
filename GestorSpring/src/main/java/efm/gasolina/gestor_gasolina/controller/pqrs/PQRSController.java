package efm.gasolina.gestor_gasolina.controller.pqrs;

import efm.gasolina.gestor_gasolina.dto.pqrs.PqrsDTO;
import efm.gasolina.gestor_gasolina.service.Pqrs.PqrsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pqrs")
public class PQRSController {

    @Autowired
    private PqrsService pqrsService;

    @PostMapping("/send")
    public ResponseEntity<String> enviarPqrs(@RequestBody PqrsDTO dto) {
        try {
            pqrsService.enviarPqrs(dto);
            return ResponseEntity.ok("PQRS enviada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}