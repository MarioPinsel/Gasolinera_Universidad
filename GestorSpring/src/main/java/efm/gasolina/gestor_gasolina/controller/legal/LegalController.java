package efm.gasolina.gestor_gasolina.controller.legal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import efm.gasolina.gestor_gasolina.dto.legal.DecreesDTO;
import efm.gasolina.gestor_gasolina.service.legal.LegalAdminService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/legal")
public class LegalController {
    
    private LegalAdminService legalAdmin;

    public LegalController(LegalAdminService legalAdmin){        
        this.legalAdmin = legalAdmin;        
    }

    @PostMapping("/newDecree")
    public ResponseEntity newDecree(@RequestBody DecreesDTO decreesDto) {
       return legalAdmin.changeGasValues(decreesDto);
    }
    



    
}
