package efm.gasolina.gestor_gasolina.repository.clients;

import efm.gasolina.gestor_gasolina.model.clients.Pqrs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PqrsRepository extends JpaRepository<Pqrs, Long> {
    List<Pqrs> findByBrandAndStatus(String brand, String status);
    List<Pqrs> findByEmailAndStatus(String email, String status);
}