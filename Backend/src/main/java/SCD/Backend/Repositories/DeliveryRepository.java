package SCD.Backend.Repositories;

import SCD.Backend.Models.Courier;
import SCD.Backend.Models.Delivery;
import SCD.Backend.Models.PackageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, String> {
    List<Delivery> findByCourier(Courier courier);

    @Query(value = "SELECT * FROM deliveries ORDER BY CASE WHEN deliveries.courier_id IS null THEN 0 END DESC", nativeQuery = true)
    List<Delivery> findAllByOrderByCourierDesc();

    @Query(value = "SELECT * FROM deliveries WHERE deliveries.status != 'DELIVERED' AND deliveries.courier_id = :id", nativeQuery = true)
    List<Delivery> findByCourierStatus(@Param("id") Integer id);

    List<Delivery> findByEmail(String email);

    List<Delivery> findByEmailAndStatus(String email, PackageStatus pending);
}
