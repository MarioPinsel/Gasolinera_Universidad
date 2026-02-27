package efm.gasolina.gestor_gasolina.controller.sesiones;

import efm.gasolina.gestor_gasolina.dto.sesion.RegisterDTO;
import efm.gasolina.gestor_gasolina.service.sesion.SesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sesion")
public class RegisterController {

    @Autowired
    SesionService sesionService;

    @PostMapping("/register")
    public RegisterDTO register(@RequestBody RegisterDTO request) {
        return sesionService.registro(request);
    }    
}
