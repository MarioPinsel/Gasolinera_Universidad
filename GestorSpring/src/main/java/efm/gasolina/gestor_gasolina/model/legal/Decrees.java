package efm.gasolina.gestor_gasolina.model.legal;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Decrees {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String typeOfGas;
    private Integer value;    
    private LocalDateTime dateOfChange;

        public Decrees() {
        }

        public Decrees(String name, String typeOfGas, Integer value, LocalDateTime dateOfChange) {
            this.name = name;
            this.typeOfGas = typeOfGas;
            this.value = value;
            this.dateOfChange = dateOfChange;
        }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeOfGas() {
        return typeOfGas;
    }

    public void setTypeOfGas(String typeOfGas) {
        this.typeOfGas = typeOfGas;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public LocalDateTime getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(LocalDateTime dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

}
