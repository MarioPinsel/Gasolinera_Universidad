package efm.gasolina.gestor_gasolina.repository.sesion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import efm.gasolina.gestor_gasolina.model.sesion.LegalAdmins;
import java.util.Optional;
import java.util.List;



@Repository
public interface LegalAdminRepository extends JpaRepository<LegalAdmins, Long>{

    Optional<LegalAdmins> findByEmail(String email);
    List<LegalAdmins> findByVerified(String verified);
    
}
