package SCD.Backend.Repositories;

import SCD.Backend.Models.Courier;
import SCD.Backend.Models.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, String> {
    List<Delivery> findByCourier(Courier courier);

    @Query(value = "SELECT * FROM orders ORDER BY CASE WHEN orders.courier_id IS null THEN 0 END DESC", nativeQuery = true)
    List<Delivery> findAllByOrderByCourierDesc();
}
