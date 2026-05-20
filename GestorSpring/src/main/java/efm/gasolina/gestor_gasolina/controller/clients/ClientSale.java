package efm.gasolina.gestor_gasolina.controller.clients;

import efm.gasolina.gestor_gasolina.dto.clients.ClientHistoryDTO;
import efm.gasolina.gestor_gasolina.dto.clients.ClientSaleDTO;
import efm.gasolina.gestor_gasolina.service.clients.ClientSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientSale {

    @Autowired
    private ClientSaleService clientSaleService;

    @PostMapping("/sale")
    public ResponseEntity<String> ventaCliente(@RequestBody ClientSaleDTO dto) {
        try {
            String result = clientSaleService.realizarCompra(dto);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/history/{email}")
    public ResponseEntity<?> obtenerHistorial(@PathVariable String email) {
        try {
            List<ClientHistoryDTO> historial = clientSaleService.obtenerHistorial(email);
            if (historial.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(historial);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}