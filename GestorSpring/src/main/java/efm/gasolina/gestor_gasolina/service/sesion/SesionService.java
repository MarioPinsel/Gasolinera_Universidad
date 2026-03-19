package efm.gasolina.gestor_gasolina.service.sesion;

import efm.gasolina.gestor_gasolina.dto.sesion.RecoverRequest;
import efm.gasolina.gestor_gasolina.dto.sesion.LoginDTO;
import efm.gasolina.gestor_gasolina.dto.sesion.LoginResponseDTO;
import efm.gasolina.gestor_gasolina.dto.sesion.RegisterDTO;
import efm.gasolina.gestor_gasolina.handler.runtime.NoSuchElement;
import efm.gasolina.gestor_gasolina.model.sesion.RegisterModel;
import efm.gasolina.gestor_gasolina.repository.redis.RedisRepository;
import efm.gasolina.gestor_gasolina.repository.sesion.SesionRepository;

import java.util.Random;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SesionService {
    @Autowired
    private SesionRepository sesionRepository;

    private final JavaMailSender mailSender;
    private final RedisRepository redisRepo;

    public SesionService(JavaMailSender mailSender, RedisRepository redisRepo) {

        this.mailSender = mailSender;
        this.redisRepo = redisRepo;
    }

    public RegisterDTO registro(RegisterDTO request) {
        RegisterModel model = new RegisterModel(request);
        sesionRepository.save(model);
        return request;
    }

    public Map<String, Object> sendEmail(String email) {

        Optional<RegisterModel> opt = sesionRepository.findByEmail(email);

        if (opt.isEmpty()) {
            throw new NoSuchElement();
        }
        Long id = opt.get().getId();
        String code = randomizer(6);
        String token = randomizer(10);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Recovery Code");
        message.setText("Your verification code is: " + code + ". You have 3 minutes before it expire");
        mailSender.send(message);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("token", token);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("id", id);
        data.put("code", code);

        redisRepo.saveWithTTL(token, data, 5);
        return response;
    }

    public ResponseEntity<Object> verifyCode(RecoverRequest request) {
        String token = request.getToken();
        String code = request.getValue();

        Map<String, Object> redisValues = (Map) redisRepo.getValue(token);

        if (redisValues.get("code").equals(code)) {
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.badRequest().build();
    }

    public LoginResponseDTO login(LoginDTO request) {

        Optional<RegisterModel> userOpt = sesionRepository.findByEmail(request.getEmail());
        
        if (userOpt.isEmpty())
            throw new RuntimeException("USER_NOT_FOUND"); 

        RegisterModel user = userOpt.get();

        if (!user.getPassword().equals(request.getPassword()))
            throw new RuntimeException("WRONG_PASSWORD"); 

        if (!"APPROVED".equals(user.getVerified()))
            throw new RuntimeException("USER_NOT_APPROVED"); 

        return new LoginResponseDTO(user.getRole().name(), user.getEmail());
    }

    public List<RegisterModel> getPendingUsers() {
        return sesionRepository.findByVerified("PENDING");
    }

    public void approveUser(Long id) {
        RegisterModel user = sesionRepository.findById(id).orElse(null);
        if (user != null) {
            user.setVerified("APPROVED");
            sesionRepository.save(user);
        }
    }

    public void rejectUser(Long id) {
        sesionRepository.deleteById(id);
    }

    public ResponseEntity<Object> changePassword(RecoverRequest request) {
        String token = request.getToken();
        String password = request.getValue();
        String id;

        try {
            Map<String, Object> redisValues = (Map) redisRepo.getValue(token);
            id = redisValues.get("id").toString();
        } catch (Exception e) {
            throw new NoSuchElement();
        }

        try {
            sesionRepository.updatePassword(id, password);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    private String randomizer(int size) {
        String values = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        return random.ints(size, 0, values.length())
                .mapToObj(values::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

    }
}
