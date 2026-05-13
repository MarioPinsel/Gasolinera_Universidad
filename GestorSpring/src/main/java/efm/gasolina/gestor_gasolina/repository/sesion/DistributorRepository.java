package efm.gasolina.gestor_gasolina.repository.sesion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import efm.gasolina.gestor_gasolina.model.sesion.Distributor;
import java.util.List;


public interface DistributorRepository extends JpaRepository<Distributor, Long>{
    Optional<Distributor> findByEmail(String email);

    List<Distributor> findByVerified(String verified);
}
