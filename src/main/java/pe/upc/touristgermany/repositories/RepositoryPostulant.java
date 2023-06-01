package pe.upc.touristgermany.repositories;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.upc.touristgermany.entities.Postulant;

import java.util.List;

@SpringBootApplication
public interface RepositoryPostulant extends JpaRepository<Postulant, Long> {
    List<Postulant> findByStatus(String status);

    Postulant findByDni(String dni);

}
