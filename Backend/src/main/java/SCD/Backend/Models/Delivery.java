package SCD.Backend.Models;

import SCD.Backend.Keys.AWB;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Delivery {
    @Id
    @GeneratedValue(generator = "generate_awb")
    @GenericGenerator(name = "generate_awb", type = AWB.class)
    @Column(name = "awb")
    private String AWB;

    @Column(name = "picking_address")
    private String pickingAddress;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_package",
            joinColumns = @JoinColumn(name = "order_awb", referencedColumnName = "awb"),
            inverseJoinColumns = @JoinColumn(name = "package_id", referencedColumnName = "id")
    )
    private Set<Package> packageSet = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Courier courier;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "price")
    private BigDecimal price;
}
