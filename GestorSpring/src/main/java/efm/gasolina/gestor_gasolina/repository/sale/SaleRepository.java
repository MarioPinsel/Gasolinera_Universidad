package efm.gasolina.gestor_gasolina.repository.sale;

import efm.gasolina.gestor_gasolina.model.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}