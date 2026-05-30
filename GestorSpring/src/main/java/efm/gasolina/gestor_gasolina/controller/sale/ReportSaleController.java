package efm.gasolina.gestor_gasolina.controller.sale;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import efm.gasolina.gestor_gasolina.service.sale.ReportSalesService;


@RestController
@RequestMapping("/sale")
public class ReportSaleController {
    
    private final ReportSalesService reportSales;

    public ReportSaleController(ReportSalesService reportSales){
        this.reportSales = reportSales;
    }

    @GetMapping("/reporting/{id}")
    public ResponseEntity getMethodName(@PathVariable Long id) throws Exception {
        reportSales.generateAndSend(id);
        return ResponseEntity.ok().build();
    }
    
}
