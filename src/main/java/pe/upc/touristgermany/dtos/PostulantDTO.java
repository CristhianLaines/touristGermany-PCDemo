package pe.upc.touristgermany.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostulantDTO {

    private Long id;
    private String name;
    private String dni;
    private double age;
    private String status;
    private Double salary;
    private boolean bag;
    private Double calification;

}
