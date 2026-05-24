package efm.gasolina.gestor_gasolina.service.Pqrs;

import efm.gasolina.gestor_gasolina.dto.clients.pqrs.PqrsDTO;
import efm.gasolina.gestor_gasolina.model.clients.Pqrs;
import efm.gasolina.gestor_gasolina.repository.clients.PqrsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PqrsService {

    @Autowired
    private JavaMailSender mailSender;

    private final PqrsRepository pqrsRepository;

    public PqrsService(PqrsRepository pqrsRepository) {
        this.pqrsRepository = pqrsRepository;
    }

    private final Map<String, String> franquiciaEmails = new HashMap<>() {{
        put("Terpel",    "pmlaser25@gmail.com");
        put("Primax",    "contacto@primax.com");
        put("Biomax",    "contacto@biomax.com");
        put("Petrobras", "contacto@petrobras.com");
    }};

    public void enviarPqrs(PqrsDTO dto) {
        // Guardar en BD
        pqrsRepository.save(new Pqrs(dto.email(), dto.brand(), dto.tipo(), dto.mensaje()));

        // Enviar correo a la franquicia
        String destinatario = franquiciaEmails.get(dto.brand());
        if (destinatario == null) {
            throw new RuntimeException("Franquicia no encontrada: " + dto.brand());
        }
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(destinatario);
        mail.setSubject("PQRS - " + dto.tipo());
        mail.setText(
                "Tipo: " + dto.tipo() + "\n" +
                        "De: " + dto.email() + "\n\n" +
                        "Mensaje:\n" + dto.mensaje()
        );
        mailSender.send(mail);
    }

    public List<Pqrs> getPendientesByBrand(String brand) {
        return pqrsRepository.findByBrandAndStatus(brand, "PENDING");
    }

    public void responderPqrs(Long id, String respuesta) {
        Pqrs pqrs = pqrsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PQRS no encontrada"));
        pqrs.setRespuesta(respuesta);
        pqrs.setStatus("RESPONDED");
        pqrsRepository.save(pqrs);
    }

    public List<Pqrs> getRespondidasByEmail(String email) {
        return pqrsRepository.findByEmailAndStatus(email, "RESPONDED");
    }
}
