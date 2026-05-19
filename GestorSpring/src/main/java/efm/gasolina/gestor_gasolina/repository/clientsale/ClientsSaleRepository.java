package efm.gasolina.gestor_gasolina.repository.clientsale;

import efm.gasolina.gestor_gasolina.model.clients.ClientsSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientsSaleRepository extends JpaRepository<ClientsSale, Long> {
    List<ClientsSale> findByClientId(Long clientId);
}