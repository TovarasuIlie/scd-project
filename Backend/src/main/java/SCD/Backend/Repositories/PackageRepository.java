package SCD.Backend.Repositories;

import SCD.Backend.Models.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package, Integer> {
}
