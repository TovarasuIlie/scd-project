package SCD.Backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DeliveryDTO {
    private String pickingAddress;
    private BigDecimal price;
    private Set<PackageDTO> packages;
}
