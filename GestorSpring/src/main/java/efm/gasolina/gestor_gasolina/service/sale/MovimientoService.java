package efm.gasolina.gestor_gasolina.service.sale;

import efm.gasolina.gestor_gasolina.dto.sale.MovimientoDTO;
import efm.gasolina.gestor_gasolina.model.sale.Sale;
import efm.gasolina.gestor_gasolina.repository.sale.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoService {

    private final SaleRepository saleRepository;

    public MovimientoService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<MovimientoDTO> obtenerHistorial(String email) {

        List<Sale> ventas = saleRepository.findByOperatorEmail(email);

        return ventas.stream()
                .map(sale -> new MovimientoDTO(
                        sale.getFuelType(),   // tipo
                        sale.getPlate(),      // placa
                        sale.getVolume(),     // volumen
                        sale.getTotalPrice(), // total
                        sale.getDate()        // fecha
                ))
                .collect(Collectors.toList());
    }
}