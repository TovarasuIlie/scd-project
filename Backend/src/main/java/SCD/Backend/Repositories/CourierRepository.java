package SCD.Backend.Repositories;

import SCD.Backend.Models.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourierRepository extends JpaRepository<Courier, Integer> {
    Optional<Courier> findByEmail(String email);
}
