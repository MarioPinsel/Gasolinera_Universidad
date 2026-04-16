package efm.gasolina.gestor_gasolina.controller.sale;

import efm.gasolina.gestor_gasolina.dto.sale.MovimientoDTO;
import efm.gasolina.gestor_gasolina.service.sale.MovimientoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping("/{email}")
    public List<MovimientoDTO> obtenerHistorial(@PathVariable String email) {
        return movimientoService.obtenerHistorial(email);
    }
}