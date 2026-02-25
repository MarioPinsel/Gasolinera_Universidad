package efm.gasolina.gestor_gasolina.repository.sesion;

import efm.gasolina.gestor_gasolina.model.sesion.RegisterModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SesionRepository extends JpaRepository<RegisterModel, Long> {
}
