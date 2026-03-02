package efm.gasolina.gestor_gasolina.repository.sesion;

import efm.gasolina.gestor_gasolina.model.sesion.RegisterModel;
import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface SesionRepository extends JpaRepository<RegisterModel, Long> {
  
    Optional<RegisterModel> findByEmail(String email);    

    @Transactional
    @Modifying
    @Query("UPDATE RegisterModel rm SET rm.password = ?2 WHERE rm.id = ?1")
    Integer updatePassword(String id, String password);



}

