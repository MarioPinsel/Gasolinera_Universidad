package efm.gasolina.gestor_gasolina.controller.sesiones;

import java.util.Map;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import efm.gasolina.gestor_gasolina.dto.sesion.RecoverRequest;
import efm.gasolina.gestor_gasolina.service.sesion.SesionService;

@RestController
@RequestMapping("/sesion")
public class RecoverSession {

    private final SesionService sesionService;

    public RecoverSession(SesionService sesionService){
        this.sesionService = sesionService;
    }
    
    @PostMapping("/forgotPassword")    
    public ResponseEntity<Map<String, Object>> forgotPassword(@RequestBody Map<String, Object> response){                  
        return ResponseEntity.ok(sesionService.sendEmail(response.get("email").toString()));
    }

    @PostMapping("/codeVerifier")
    public ResponseEntity<Object> codeVerifier(@RequestBody RecoverRequest request) {                
        return sesionService.verifyCode(request);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Object> postMethodName(@RequestBody RecoverRequest request) {            
        return sesionService.changePassword(request);
    }
    
    
}
