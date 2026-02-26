package efm.gasolina.gestor_gasolina.service.sesion;

import efm.gasolina.gestor_gasolina.dto.sesion.LoginDTO;
import efm.gasolina.gestor_gasolina.dto.sesion.LoginResponseDTO;
import efm.gasolina.gestor_gasolina.dto.sesion.RegisterDTO;
import efm.gasolina.gestor_gasolina.model.sesion.RegisterModel;
import efm.gasolina.gestor_gasolina.repository.sesion.SesionRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SesionService {
    @Autowired
    private SesionRepository sesionRepository;

    public RegisterDTO registro(RegisterDTO request) {
        RegisterModel model = new RegisterModel(request);
        sesionRepository.save(model);
        return request;
    }

    public LoginResponseDTO login(LoginDTO request) {

    Optional<RegisterModel> user = sesionRepository.findByEmail(request.getEmail());

    if (user.isEmpty()) {
        return null;
    }

    if (!user.get().getPassword().equals(request.getPassword())) {
        return null;
    }

    return new LoginResponseDTO(user.get().getRole().name());
    }
}
