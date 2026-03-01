package efm.gasolina.gestor_gasolina.repository.sesion;

import efm.gasolina.gestor_gasolina.model.sesion.RegisterModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SesionRepository extends JpaRepository<RegisterModel, Long> {
  
    Optional<RegisterModel> findByEmail(String email);    
    
    @Query("UPDATE RegisterModel rm SET rm.password = ?2 WHERE rm.id = ?1")
    Void updatePassword(String id, String password);



}

