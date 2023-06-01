package pe.upc.touristgermany.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="ePostulant")
public class Postulant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String dni;
    private Integer age;
    private String status;
    private Double salary;
    private boolean bag;
    private Double calification;

    public Postulant() {
    }

    public Postulant(Long id, String name, String dni, Integer age, String status, Double salary, boolean bag, Double calification) {
        this.id = id;
        this.name = name;
        this.dni = dni;
        this.age = age;
        this.status = status;
        this.salary = salary;
        this.bag = bag;
        this.calification = calification;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public boolean isBag() {
        return bag;
    }

    public void setBag(boolean bag) {
        this.bag = bag;
    }

    public Double getCalification() {
        return calification;
    }

    public void setCalification(Double calification) {
        this.calification = calification;
    }

    @Override
    public String toString() {
        return "Postulant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dni='" + dni + '\'' +
                ", age=" + age + '\'' +
                ", status='" + status + '\'' +
                ", salary=" + salary + '\'' +
                ", bag=" + bag + '\'' +
                ", calification=" + calification +
                '}';
    }
}
