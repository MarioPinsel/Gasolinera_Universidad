package efm.gasolina.gestor_gasolina.service.sesion;

import efm.gasolina.gestor_gasolina.dto.sesion.RegisterDTO;
import efm.gasolina.gestor_gasolina.model.sesion.RegisterModel;
import efm.gasolina.gestor_gasolina.repository.sesion.SesionRepository;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SesionService {
    @Autowired
    private SesionRepository sesionRepository;

    private final JavaMailSender mailSender;

    public SesionService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }
    
    public RegisterDTO registro(RegisterDTO request) {
        RegisterModel model = new RegisterModel(request);
        sesionRepository.save(model);
        return request;
    }

    public void sendEmail(String email){
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
    }
}
