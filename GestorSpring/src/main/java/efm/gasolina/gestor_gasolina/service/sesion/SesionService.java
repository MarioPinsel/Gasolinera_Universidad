package efm.gasolina.gestor_gasolina.service.sesion;

import efm.gasolina.gestor_gasolina.dto.sesion.LoginDTO;
import efm.gasolina.gestor_gasolina.dto.sesion.LoginResponseDTO;
import efm.gasolina.gestor_gasolina.dto.sesion.RegisterDTO;
import efm.gasolina.gestor_gasolina.model.sesion.RegisterModel;
import efm.gasolina.gestor_gasolina.repository.sesion.SesionRepository;

import java.util.Random;
import java.util.HashMap;
import java.util.Optional;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SesionService {
    @Autowired
    private SesionRepository sesionRepository;

    private final JavaMailSender mailSender;

    public SesionService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public RegisterDTO registro(RegisterDTO request) {
        RegisterModel model = new RegisterModel(request);
        sesionRepository.save(model);
        return request;
    }

    public Map<String, Object> sendEmail(String email) {
                
        String values = "0123456789";
        Random random = new Random();        
        String code = random.ints(6, 0, values.length())
                .mapToObj(values::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();       

        SimpleMailMessage message = new SimpleMailMessage();        
        message.setTo(email);
        message.setSubject("Password Recovery Code");
        message.setText("Your verification code is: " + code);
        mailSender.send(message);

         String token = random.ints(10, 0, values.length())
                .mapToObj(values::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        Map<String, Object> response = new HashMap<String, Object>();

        response.put(
            "token", token
        );
    
        return response;
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
