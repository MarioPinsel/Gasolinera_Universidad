package efm.gasolina.gestor_gasolina.repository.legal;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import efm.gasolina.gestor_gasolina.model.legal.Decrees;

@Repository
public interface LegalRepository extends JpaRepository<Decrees, Long>{
    
    @Query("SELECT d.value FROM Decrees d WHERE d.typeOfGas = ?1 ORDER BY d.id DESC LIMIT 1")
    Optional<Integer> findValueOfGas(String typeOfgAS);

}
