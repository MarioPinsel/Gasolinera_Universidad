package efm.gasolina.gestor_gasolina.controller.sesiones;

import org.springframework.web.bind.annotation.RestController;

import efm.gasolina.gestor_gasolina.dto.sesion.LoginDTO;
import efm.gasolina.gestor_gasolina.dto.sesion.LoginResponseDTO;
import efm.gasolina.gestor_gasolina.service.sesion.SesionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/sesion")
public class LoginController {

    @Autowired
    SesionService sesionService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO request) {
        try {
            return ResponseEntity.ok(sesionService.login(request));

        } catch (RuntimeException e) {
            return switch (e.getMessage()) {
                case "USER_NOT_FOUND" -> ResponseEntity.status(404).build();
                case "WRONG_PASSWORD" -> ResponseEntity.status(401).build();
                case "USER_NOT_APPROVED" -> ResponseEntity.status(403).build();
                default -> ResponseEntity.badRequest().build();
            };
        }
    }
}
