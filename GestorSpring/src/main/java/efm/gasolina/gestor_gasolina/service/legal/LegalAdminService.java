package efm.gasolina.gestor_gasolina.service.legal;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import efm.gasolina.gestor_gasolina.dto.legal.DecreesDTO;
import efm.gasolina.gestor_gasolina.model.legal.Decrees;
import efm.gasolina.gestor_gasolina.repository.legal.LegalRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class LegalAdminService {
    
    private final LegalRepository legalRepo;

    public LegalAdminService(LegalRepository legalRepo){
        this.legalRepo = legalRepo;
    }

    public ResponseEntity changeGasValues(DecreesDTO decreesDto){
        String name = decreesDto.getName();
        Integer value = decreesDto.getValue();
        String typeOfGas = decreesDto.getTypeOfGas();
        
        if (name.isBlank() || name.isEmpty() || name.equals(" ")) {
            return ResponseEntity.badRequest().body("Invalid Name");
        }

        if (typeOfGas.isBlank() || typeOfGas.isEmpty() || typeOfGas.equals(" ") 
            && typeOfGas.equals("Corriente") || typeOfGas.equals("Diesel")) {
            return ResponseEntity.badRequest().body("Invalid Type of Gas");
        }

        if (value <= 0) {
            return ResponseEntity.badRequest().body("Invalid Value, it has to be higher than 0");
        }

        Decrees newDecree = new Decrees(name, typeOfGas, value, LocalDateTime.now());

        legalRepo.save(newDecree);

        return ResponseEntity.ok().build();
    }
    
}
