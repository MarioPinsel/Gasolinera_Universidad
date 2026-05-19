package efm.gasolina.gestor_gasolina.service.clients;

import efm.gasolina.gestor_gasolina.dto.clients.ClientHistoryDTO;
import efm.gasolina.gestor_gasolina.dto.clients.ClientSaleDTO;
import efm.gasolina.gestor_gasolina.model.clients.ClientsSale;
import efm.gasolina.gestor_gasolina.model.sesion.Clients;
import efm.gasolina.gestor_gasolina.model.station.Station;
import efm.gasolina.gestor_gasolina.repository.clientsale.ClientsSaleRepository;
import efm.gasolina.gestor_gasolina.repository.sesion.ClientsRepository;
import efm.gasolina.gestor_gasolina.repository.station.StationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientSaleService {

    private final ClientsSaleRepository clientsSaleRepository;
    private final ClientsRepository clientsRepository;
    private final StationRepository stationRepository;

    public ClientSaleService(ClientsSaleRepository clientsSaleRepository,
                             ClientsRepository clientsRepository,
                             StationRepository stationRepository) {
        this.clientsSaleRepository = clientsSaleRepository;
        this.clientsRepository     = clientsRepository;
        this.stationRepository     = stationRepository;
    }

    @Transactional
    public String realizarCompra(ClientSaleDTO dto) {
        Clients client = clientsRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Station station = stationRepository.findByBrandAndZone(dto.brand(), dto.zone())
                .orElseThrow(() -> new RuntimeException("Estación no encontrada"));

        if (dto.fuelType().equals("Diesel")) {
            if (station.getDiesel_quantity() < dto.volume())
                throw new RuntimeException("Stock insuficiente de Diesel");
            station.setDiesel_quantity(station.getDiesel_quantity() - dto.volume());
        } else {
            if (station.getRegular_quantity() < dto.volume())
                throw new RuntimeException("Stock insuficiente de Corriente");
            station.setRegular_quantity(station.getRegular_quantity() - dto.volume());
        }

        Integer priceDiff  = dto.fuelType().equals("Diesel")
                ? station.getDiesel_price_difference()
                : station.getRegular_price_difference();
        Integer totalPrice = priceDiff * dto.volume();

        ClientsSale sale = new ClientsSale(client, station, dto.fuelType(),
                dto.vehicleType(), dto.volume(), totalPrice);
        clientsSaleRepository.save(sale);

        return "Compra realizada correctamente";
    }

    public List<ClientHistoryDTO> obtenerHistorial(String email) {
        Clients client = clientsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        return clientsSaleRepository.findByClientId(client.getId()).stream()
                .map(s -> new ClientHistoryDTO(
                        s.getBrand(),
                        s.getZone(),
                        s.getFuelType(),
                        s.getVehicleType(),
                        s.getVolume(),
                        s.getTotalPrice(),
                        s.getDate()
                ))
                .collect(Collectors.toList());
    }
}