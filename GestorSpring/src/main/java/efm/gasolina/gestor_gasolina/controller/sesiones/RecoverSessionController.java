package efm.gasolina.gestor_gasolina.controller.sesiones;

import java.util.Map;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import efm.gasolina.gestor_gasolina.dto.sesion.RecoverRequestDTO;
import efm.gasolina.gestor_gasolina.service.sesion.SessionRecoverService;

@RestController
@RequestMapping("/sesion")
public class RecoverSessionController {

    private final SessionRecoverService sessionRecover;

    public RecoverSessionController(SessionRecoverService sessionRecover){
        this.sessionRecover = sessionRecover;
    }
    
    @PostMapping("/forgotPassword")    
    public ResponseEntity<Map<String, Object>> forgotPassword(@RequestBody Map<String, Object> response){                  
        return ResponseEntity.ok(sessionRecover.sendEmail(response.get("email").toString()));
    }

    @PostMapping("/codeVerifier")
    public ResponseEntity<Object> codeVerifier(@RequestBody RecoverRequestDTO request) {                
        return sessionRecover.verifyCode(request);
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<Object> postMethodName(@RequestBody RecoverRequestDTO request) {            
        return sessionRecover.changePassword(request);
    }
    
    
}
