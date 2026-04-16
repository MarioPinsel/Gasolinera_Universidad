package efm.gasolina.gestor_gasolina.dto.legal;

public class DecreesDTO {
    private String name;
    private String typeOfGas;
    private Integer value;

    public DecreesDTO() {
    }

    public DecreesDTO(String name, String typeOfGas, Integer value) {
        this.name = name;
        this.typeOfGas = typeOfGas;
        this.value = value;
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
}
