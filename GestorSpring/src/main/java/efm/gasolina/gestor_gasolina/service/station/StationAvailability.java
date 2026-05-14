package efm.gasolina.gestor_gasolina.service.station;

import efm.gasolina.gestor_gasolina.dto.station.StationAvailabilityDTO;
import efm.gasolina.gestor_gasolina.model.sesion.Operator;
import efm.gasolina.gestor_gasolina.model.station.Station;
import efm.gasolina.gestor_gasolina.repository.sesion.OperatorRepository;

import efm.gasolina.gestor_gasolina.repository.station.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationAvailability {

    @Autowired
    private OperatorRepository operatorRepo;

    @Autowired
    private StationRepository stationRepository;

    public StationAvailabilityDTO getAvailability(String operatorEmail) {
        Operator operator = operatorRepo.findByEmail(operatorEmail)
            .orElseThrow(() -> new RuntimeException("OPERATOR_NOT_FOUND"));

        Station station = stationRepository.findById(operator.getId_station())
            .orElseThrow(() -> new RuntimeException("STATION_NOT_FOUND"));

        return new StationAvailabilityDTO(
            station.getZone(),
            station.getBrand(),
            station.getDiesel_quantity(),
            station.getDiesel_capacity(),
            station.getRegular_quantity(),
            station.getRegular_capacity()
        );
    }
}