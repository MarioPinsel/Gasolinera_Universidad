package efm.gasolina.gestor_gasolina.service.sale;

import efm.gasolina.gestor_gasolina.dto.sale.SaleDTO;
import efm.gasolina.gestor_gasolina.model.sale.Sale;
import efm.gasolina.gestor_gasolina.model.sesion.Operator;
import efm.gasolina.gestor_gasolina.model.sesion.RegisterModel;
import efm.gasolina.gestor_gasolina.model.station.Station;
import efm.gasolina.gestor_gasolina.repository.legal.LegalRepository;
import efm.gasolina.gestor_gasolina.repository.sale.SaleRepository;
import efm.gasolina.gestor_gasolina.repository.sesion.OperatorRepository;
import efm.gasolina.gestor_gasolina.repository.sesion.SesionRepository;
import efm.gasolina.gestor_gasolina.repository.station.StationRepository;
import efm.gasolina.gestor_gasolina.repository.station.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private SesionRepository sesionRepository;
    @Autowired
    private OperatorRepository operatorRepo;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private LegalRepository legalRepository;

    private Integer dieselBase;
    private Integer regularBase;

    public Sale registerSale(SaleDTO request) {

        loadCurrentPrices();

        Operator operator = operatorRepo.findByEmail(request.operatorEmail())
                .orElseThrow(() -> new RuntimeException("OPERATOR_NOT_FOUND"));

        Station station = stationRepository.findById(operator.getId_station())
                .orElseThrow(() -> new RuntimeException("STATION_NOT_FOUND"));

        Integer pricePerGallon;

        if (request.fuelType().equals("Corriente")) {
            Integer subsidy = vehicleRepository
                    .findRegularSubsidyByVehicle(request.vehicleType()).orElse(0);
            Integer diff = stationRepository
                    .findRegularDiffByStation(station.getId()).orElse(0);
            pricePerGallon = regularBase - subsidy + diff;

        } else if (request.fuelType().equals("Diesel")) {
            Integer subsidy = vehicleRepository
                    .findDieselSubsidyByVehicle(request.vehicleType()).orElse(0);
            Integer diff = stationRepository
                    .findDieselDiffByStation(station.getId()).orElse(0);
            pricePerGallon = dieselBase - subsidy + diff;

        } else {
            throw new RuntimeException("INVALID_FUEL_TYPE");
        }

        if (request.fuelType().equals("Corriente") &&
                request.volume() > station.getRegular_quantity())
            throw new RuntimeException("INSUFFICIENT_FUEL");

        if (request.fuelType().equals("Diesel") &&
                request.volume() > station.getDiesel_quantity())
            throw new RuntimeException("INSUFFICIENT_FUEL");

        if (request.fuelType().equals("Corriente")) {
            station.setRegular_quantity(station.getRegular_quantity() - request.volume());
        } else {
            station.setDiesel_quantity(station.getDiesel_quantity() - request.volume());
        }
        stationRepository.save(station);

        Sale sale = new Sale(
                request.fuelType(),
                request.vehicleType(),
                request.volume(),
                pricePerGallon,
                request.plate(),
                station,
                operator);

        return saleRepository.save(sale);
    }

    public List<String> getVehicleTypes() {
        return vehicleRepository.findAllVehicleTypes();
    }

    public Integer calculatePrice(String operatorEmail, String fuelType, String vehicleType) {

        loadCurrentPrices();

        Operator operator = operatorRepo.findByEmail(operatorEmail)
                .orElseThrow(() -> new RuntimeException("OPERATOR_NOT_FOUND"));

        Station station = stationRepository.findById(operator.getId_station())
                .orElseThrow(() -> new RuntimeException("STATION_NOT_FOUND"));

        if (fuelType.equals("Corriente")) {
            Integer subsidy = vehicleRepository
                    .findRegularSubsidyByVehicle(vehicleType).orElse(0);
            Integer diff = stationRepository
                    .findRegularDiffByStation(station.getId()).orElse(0);
            return regularBase - subsidy + diff;

        } else if (fuelType.equals("Diesel")) {
            Integer subsidy = vehicleRepository
                    .findDieselSubsidyByVehicle(vehicleType).orElse(0);
            Integer diff = stationRepository
                    .findDieselDiffByStation(station.getId()).orElse(0);
            return dieselBase - subsidy + diff;

        } else {
            throw new RuntimeException("INVALID_FUEL_TYPE");
        }
    }

    private void loadCurrentPrices() {
        this.dieselBase = legalRepository.findValueOfGas("Diesel")
                .orElseThrow(
                        () -> new IllegalStateException("No new price for Diesel"));

        this.regularBase = legalRepository.findValueOfGas("Corriente")
                .orElseThrow(
                        () -> new IllegalStateException("No new price for Corriente"));
    }
}