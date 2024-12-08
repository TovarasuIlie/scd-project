package SCD.Backend.Repositories;

import SCD.Backend.Models.Courier;
import SCD.Backend.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourierRepository extends JpaRepository<Courier, Integer> {
    Optional<Courier> findByEmail(String email);
    List<Courier> findAllByOrderByIdAsc();

    @Query(value = "UPDATE couriers SET manager_id = null WHERE manager_id = :id", nativeQuery = true)
    @Modifying
    void updateAllCouriersManager(@Param("id") Integer id);

    List<Courier> findAllByRole(Role role);

    @Query(value = "SELECT couriers.* FROM couriers LEFT JOIN deliveries ON couriers.id = deliveries.courier_id WHERE couriers.role = \"ROLE_COURIER\" AND couriers.manager_id = :manager_id GROUP BY IFNULL(deliveries.courier_id, UUID()) HAVING COUNT(CASE WHEN deliveries.status != \"DELIVERED\" THEN deliveries.awb END) < 2", nativeQuery = true)
    List<Courier> findAllCouriersWithoutPendingPackages(@Param("manager_id") Integer managerId);
}
