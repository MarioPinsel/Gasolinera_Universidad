package efm.gasolina.gestor_gasolina.service.Pqrs;

import efm.gasolina.gestor_gasolina.dto.clients.pqrs.PqrsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PqrsService {

    @Autowired
    private JavaMailSender mailSender;

    private final Map<String, String> franquiciaEmails = new HashMap<>() {{
        put("Terpel",    "pmlaser25@gmail.com");
        put("Primax",    "contacto@primax.com");
        put("Biomax",    "contacto@biomax.com");
        put("Petrobras", "contacto@petrobras.com");
    }};

    public void enviarPqrs(PqrsDTO dto) {
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
}
