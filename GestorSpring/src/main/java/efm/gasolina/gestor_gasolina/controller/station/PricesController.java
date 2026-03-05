package efm.gasolina.gestor_gasolina.controller.station;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import efm.gasolina.gestor_gasolina.dto.station.StationRequestDTO;
import efm.gasolina.gestor_gasolina.service.station.PricesService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/consult")
public class PricesController {

    private final PricesService priceService;

    public PricesController(PricesService priceService){
        this.priceService = priceService; 
    }
    
    @GetMapping("/prices/{zone}/{type}") 
    public ResponseEntity<List<StationRequestDTO>> getPrices(@PathVariable String zone, @PathVariable String type) {
        return priceService.getPricesAndFranchise(zone, type);
    }
        
}
