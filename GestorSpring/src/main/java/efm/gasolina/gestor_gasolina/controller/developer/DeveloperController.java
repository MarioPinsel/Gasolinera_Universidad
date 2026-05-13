package efm.gasolina.gestor_gasolina.controller.developer;

import efm.gasolina.gestor_gasolina.dto.sesion.LoginResponseDTO;
import efm.gasolina.gestor_gasolina.model.sesion.ICredentials;
import efm.gasolina.gestor_gasolina.service.sesion.SesionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/developer")
public class DeveloperController {

    @Autowired
    SesionService sesionService;

    @GetMapping("/pending")
    public ResponseEntity<List<LoginResponseDTO>> getPending() {
        List<LoginResponseDTO> pending = sesionService.getPendingUsers();
        return ResponseEntity.ok(pending);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approve(@PathVariable Long id, @RequestParam String role) {
        sesionService.approveUser(id, role);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<?> reject(@PathVariable Long id, @RequestParam String role) {
        sesionService.rejectUser(id, role);
        return ResponseEntity.ok().build();
    }
}