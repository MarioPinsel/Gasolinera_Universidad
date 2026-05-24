package efm.gasolina.gestor_gasolina.service.sesion;

import efm.gasolina.gestor_gasolina.dto.sesion.LoginDTO;
import efm.gasolina.gestor_gasolina.dto.sesion.LoginResponseDTO;
import efm.gasolina.gestor_gasolina.dto.sesion.RegisterDTO;
import efm.gasolina.gestor_gasolina.model.sesion.Clients;
import efm.gasolina.gestor_gasolina.model.sesion.Distributor;
import efm.gasolina.gestor_gasolina.model.sesion.ICredentials;
import efm.gasolina.gestor_gasolina.model.sesion.LegalAdmins;
import efm.gasolina.gestor_gasolina.model.sesion.Operator;
import efm.gasolina.gestor_gasolina.repository.sesion.ClientsRepository;
import efm.gasolina.gestor_gasolina.repository.sesion.DistributorRepository;
import efm.gasolina.gestor_gasolina.repository.sesion.LegalAdminRepository;
import efm.gasolina.gestor_gasolina.repository.sesion.OperatorRepository;
import efm.gasolina.gestor_gasolina.repository.sesion.SesionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import efm.gasolina.gestor_gasolina.repository.station.StationRepository;
import org.springframework.stereotype.Service;

@Service
public class SesionService {

    // Users
    private final LegalAdminRepository legalAdminRepo;
    private final ClientsRepository clientsRepo;
    private final DistributorRepository distributorRepo;
    private final OperatorRepository operatorRepo;

    // Utils
    private final StationRepository stationRepo;
    private final SesionRepository sesionRepo;

    public SesionService(SesionRepository sesionRepository, StationRepository stationRepository,
            LegalAdminRepository legalAdminRepository, ClientsRepository clientsRepository,
            DistributorRepository distributorRepo, OperatorRepository operatorRepo) {
        this.sesionRepo = sesionRepository;
        this.legalAdminRepo = legalAdminRepository;
        this.clientsRepo = clientsRepository;
        this.distributorRepo = distributorRepo;
        this.operatorRepo = operatorRepo;

        this.stationRepo = stationRepository;
    }

    public RegisterDTO registro(RegisterDTO request) {
        switch (request.role()) {
            case ADMINISTRADORLEGAL:
                LegalAdmins legalAdmin = new LegalAdmins(request);
                legalAdminRepo.save(legalAdmin);
                break;
            case CLIENTE:
                Clients client = new Clients(request);
                clientsRepo.save(client);
                break;
            case DISTRIBUIDOR:
                Distributor distributor = new Distributor(request);
                distributorRepo.save(distributor);
                break;
            case OPERADOR:
                Operator operator = new Operator(request);
                operator.setId_station(
                        stationRepo.findIdByBrandAndZone(operator.getBrand(), operator.getZone()));
                operatorRepo.save(operator);
                break;
            default:
                break;
        }
        return request;
    }

    public LoginResponseDTO login(LoginDTO request) {
        Optional<?> user = Optional.empty();
        String role;

        if (request.email().contains("admin")) {
            user = legalAdminRepo.findByEmail(request.email());
            role = "ADMINISTRADORLEGAL";
        } else if (request.email().contains("operator")) {
            user = operatorRepo.findByEmail(request.email());
            role = "OPERADOR";
        } else if (request.email().contains("distributor")) {
            user = distributorRepo.findByEmail(request.email());
            role = "DISTRIBUIDOR";
        } else {
            user = clientsRepo.findByEmail(request.email());
            role = "CLIENTE";
        }

        if (user.isEmpty())
            throw new RuntimeException("USER_NOT_FOUND");

        ICredentials currentUser = (ICredentials) user.get();

        if (!currentUser.getPassword().equals(request.password()))
            throw new RuntimeException("WRONG_PASSWORD");

        if (!"APPROVED".equals(currentUser.getVerified()))
            throw new RuntimeException("USER_NOT_APPROVED");

        if (role.equals("OPERADOR")) {
            Operator operator = (Operator) currentUser;
            return new LoginResponseDTO(
                    operator.getId(),
                    role,
                    currentUser.getEmail(),
                    operator.getId_station(),
                    operator.getName(),
                    operator.getBrand()  // <- agregar
            );
        }
        return new LoginResponseDTO(
                currentUser.getId(),
                role,
                currentUser.getEmail(),
                null,
                currentUser.getName(),
                null  // <- agregar null para los demás roles
        );
    }

    public List<LoginResponseDTO> getPendingUsers() {
        List<LoginResponseDTO> pending = new ArrayList<>();

        clientsRepo.findByVerified("PENDING").stream()
                .map(c -> new LoginResponseDTO(c.getId(), "CLIENTE", c.getEmail(), null, c.getName(), null))
                .forEach(pending::add);

        operatorRepo.findByVerified("PENDING").stream()
                .map(o -> new LoginResponseDTO(o.getId(),"OPERADOR", o.getEmail(), o.getId_station(), o.getName(), o.getBrand()))
                .forEach(pending::add);

        distributorRepo.findByVerified("PENDING").stream()
                .map(d -> new LoginResponseDTO(d.getId(),"DISTRIBUIDOR", d.getEmail(), null, d.getName(), null))
                .forEach(pending::add);

        legalAdminRepo.findByVerified("PENDING").stream()
                .map(a -> new LoginResponseDTO(a.getId(),"ADMINISTRADORLEGAL", a.getEmail(), null, a.getName(), null))
                .forEach(pending::add);

        return pending;
    }

    public void approveUser(Long id, String role) {
        switch (role) {
            case "CLIENTE" -> {
                Clients client = clientsRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
                client.setVerified("APPROVED");
                clientsRepo.save(client);
            }
            case "OPERADOR" -> {
                Operator operator = operatorRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
                operator.setVerified("APPROVED");
                operatorRepo.save(operator);
            }
            case "DISTRIBUIDOR" -> {
                Distributor distributor = distributorRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
                distributor.setVerified("APPROVED");
                distributorRepo.save(distributor);
            }
            case "ADMINISTRADORLEGAL" -> {
                LegalAdmins admin = legalAdminRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
                admin.setVerified("APPROVED");
                legalAdminRepo.save(admin);
            }
            default -> throw new RuntimeException("INVALID_ROLE");
        }
    }

    public void rejectUser(Long id, String role) {
        switch (role) {
            case "CLIENTE" -> {
                Clients client = clientsRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
                client.setVerified("REJECTED");
                clientsRepo.save(client);
            }
            case "OPERADOR" -> {
                Operator operator = operatorRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
                operator.setVerified("REJECTED");
                operatorRepo.save(operator);
            }
            case "DISTRIBUIDOR" -> {
                Distributor distributor = distributorRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
                distributor.setVerified("REJECTED");
                distributorRepo.save(distributor);
            }
            case "ADMINISTRADORLEGAL" -> {
                LegalAdmins admin = legalAdminRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
                admin.setVerified("REJECTED");
                legalAdminRepo.save(admin);
            }
            default -> throw new RuntimeException("INVALID_ROLE");
        }
    }

}