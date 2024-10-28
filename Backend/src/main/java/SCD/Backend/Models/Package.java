package SCD.Backend.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "packages")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "pay_on_delivery", nullable = false)
    private boolean payOnDelivery;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PackageStatus status;
}
