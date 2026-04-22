package efm.gasolina.gestor_gasolina.repository.sesion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import efm.gasolina.gestor_gasolina.model.sesion.LegalAdmins;

@Repository
public interface LegalAdminRepository extends JpaRepository<LegalAdmins, Long>{
    
}
