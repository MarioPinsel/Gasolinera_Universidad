package efm.gasolina.gestor_gasolina.repository.sesion;

import org.springframework.data.jpa.repository.JpaRepository;
import efm.gasolina.gestor_gasolina.model.sesion.Clients;

public interface ClientsRepository extends JpaRepository<Clients, Long>{
    
}
