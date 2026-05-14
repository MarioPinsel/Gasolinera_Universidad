package efm.gasolina.gestor_gasolina.repository.sesion;

import org.springframework.data.jpa.repository.JpaRepository;
import efm.gasolina.gestor_gasolina.model.sesion.Clients;
import java.util.Optional;
import java.util.List;



public interface ClientsRepository extends JpaRepository<Clients, Long>{
    Optional<Clients> findByEmail(String email);
    List<Clients> findByVerified(String verified);
}
