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
    LoginResponseDTO response = sesionService.login(request);

    if (response == null) {
        return ResponseEntity.status(401).build();
    }

    return ResponseEntity.ok(response);
    }
}
