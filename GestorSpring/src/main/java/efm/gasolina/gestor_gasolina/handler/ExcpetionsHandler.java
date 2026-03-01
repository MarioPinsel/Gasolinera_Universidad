package efm.gasolina.gestor_gasolina.handler;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import efm.gasolina.gestor_gasolina.dto.error.ErrorDTO;

@RestControllerAdvice
public class ExcpetionsHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDTO> noSuchElement(Exception ex){
        ErrorDTO error = new ErrorDTO(
            HttpStatus.NOT_FOUND.value(),
            ex.getCause().getMessage().toString(),
            ex.getMessage().toString()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }
    
    
}
