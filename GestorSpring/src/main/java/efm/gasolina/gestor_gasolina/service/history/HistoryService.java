package efm.gasolina.gestor_gasolina.service.history;

import efm.gasolina.gestor_gasolina.dto.history.HistoryDTO;
import efm.gasolina.gestor_gasolina.repository.sale.SaleRepository;
import efm.gasolina.gestor_gasolina.repository.wholesaler.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryService {

    private final SaleRepository saleRepository;
    private final DeliveryRepository deliveryRepository;

    public HistoryService(SaleRepository saleRepository,
                          DeliveryRepository deliveryRepository) {
        this.saleRepository     = saleRepository;
        this.deliveryRepository = deliveryRepository;
    }

    public List<HistoryDTO> obtenerHistorial(String email, Long stationId) {
        List<HistoryDTO> historial = new ArrayList<>();

        // Salidas — ventas del operador
        saleRepository.findByOperatorEmail(email).forEach(s ->
                historial.add(new HistoryDTO(
                        "SALIDA",
                        s.getPlate(),
                        s.getVolume(),
                        s.getTotalPrice(),
                        s.getDate()
                ))
        );

        // Entradas — entregas recibidas en la estación
        deliveryRepository.findByStationId(stationId).forEach(d ->
                historial.add(new HistoryDTO(
                        "ENTRADA",
                        d.getVehicle(),
                        d.getVolume(),
                        null,
                        d.getDate()
                ))
        );

        historial.sort((a, b) -> b.fecha().compareTo(a.fecha()));

        return historial;
    }
}