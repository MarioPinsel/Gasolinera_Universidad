package efm.gasolina.gestor_gasolina.repository.sesion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import efm.gasolina.gestor_gasolina.model.sesion.Operator;
import java.util.List;


public interface OperatorRepository extends JpaRepository<Operator, Long>{
    Optional<Operator> findByEmail(String email);
    List<Operator> findByVerified(String verified);
}
