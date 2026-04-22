package efm.gasolina.gestor_gasolina.service.sesion;

import efm.gasolina.gestor_gasolina.dto.sesion.LoginDTO;
import efm.gasolina.gestor_gasolina.dto.sesion.LoginResponseDTO;
import efm.gasolina.gestor_gasolina.dto.sesion.RegisterDTO;
import efm.gasolina.gestor_gasolina.model.sesion.Clients;
import efm.gasolina.gestor_gasolina.model.sesion.LegalAdmins;
import efm.gasolina.gestor_gasolina.model.sesion.RegisterModel;
import efm.gasolina.gestor_gasolina.repository.sesion.ClientsRepository;
import efm.gasolina.gestor_gasolina.repository.sesion.LegalAdminRepository;
import efm.gasolina.gestor_gasolina.repository.sesion.SesionRepository;

import java.util.List;
import java.util.Optional;

import efm.gasolina.gestor_gasolina.repository.station.StationRepository;
import org.springframework.stereotype.Service;

@Service
public class SesionService {

    private final SesionRepository sesionRepo;
    private final StationRepository stationRepo;
    private final LegalAdminRepository legalAdminRepo;
    private final ClientsRepository clientsRepo;

    public SesionService(SesionRepository sesionRepository, StationRepository stationRepository, 
                        LegalAdminRepository legalAdminRepository, ClientsRepository clientsRepository ){
        this.sesionRepo = sesionRepository;
        this.stationRepo = stationRepository;
        this.legalAdminRepo = legalAdminRepository;
        this.clientsRepo = clientsRepository;
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
        
            default:
                break;
        }
        return request;
    }

    
    // public RegisterDTO registro(RegisterDTO request) {
    //     RegisterModel model = new RegisterModel(request);
    //     model.setIdStation(stationRepository.findIdByBrandAndZone(model.getBrand(), model.getZone()));
    //     sesionRepository.save(model);
    //     return request;
    // }

    public LoginResponseDTO login(LoginDTO request) {

        Optional<RegisterModel> userOpt = sesionRepo.findByEmail(request.email());

        if (userOpt.isEmpty())
            throw new RuntimeException("USER_NOT_FOUND");

        RegisterModel user = userOpt.get();

        if (!user.getPassword().equals(request.password()))
            throw new RuntimeException("WRONG_PASSWORD");

        if (!"APPROVED".equals(user.getVerified()))
            throw new RuntimeException("USER_NOT_APPROVED");

        return new LoginResponseDTO(user.getRole().name(), user.getEmail(), user.getIdStation());
    }

    public List<RegisterModel> getPendingUsers() {
        return sesionRepo.findByVerified("PENDING");
    }

    public void approveUser(Long id) {
        RegisterModel user = sesionRepo.findById(id).orElse(null);
        if (user != null) {
            user.setVerified("APPROVED");
            sesionRepo.save(user);
        }
    }

    public void rejectUser(Long id) {
        sesionRepo.deleteById(id);
    }
    
}