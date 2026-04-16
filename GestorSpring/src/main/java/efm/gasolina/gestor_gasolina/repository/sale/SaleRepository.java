package efm.gasolina.gestor_gasolina.repository.sale;

import efm.gasolina.gestor_gasolina.model.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByOperatorEmail(String email);

}