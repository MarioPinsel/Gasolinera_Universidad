package efm.gasolina.gestor_gasolina.controller.developer;

import efm.gasolina.gestor_gasolina.model.sesion.RegisterModel;
import efm.gasolina.gestor_gasolina.repository.sesion.SesionRepository;
import efm.gasolina.gestor_gasolina.service.sesion.SesionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/developer")
public class DeveloperController {

    @Autowired
    SesionService sesionService;

    @GetMapping("/pending")
    public ResponseEntity<List<RegisterModel>> getPending() {
        List<RegisterModel> pending = sesionService.getPendingUsers();
        return ResponseEntity.ok(pending);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approve(@PathVariable Long id) {
        sesionService.approveUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<?> reject(@PathVariable Long id) {
        sesionService.rejectUser(id);
        return ResponseEntity.ok().build();
    }
}