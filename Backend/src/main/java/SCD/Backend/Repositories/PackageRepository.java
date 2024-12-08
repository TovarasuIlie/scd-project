package SCD.Backend.Repositories;

import SCD.Backend.Models.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PackageRepository extends JpaRepository<Package, Integer> {

    @Query(value = "UPDATE packages JOIN delivery_packages ON packages.id = delivery_packages.package_id SET packages.status = :status WHERE delivery_packages.delivery_awb = :awb", nativeQuery = true)
    @Modifying
    void updatePackagesStatusFromDelivery(@Param("awb") String awb, @Param("status") String status);
}
