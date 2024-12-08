package SCD.Backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditDeliveryDTO {
    private String awb;
    private String pickingAddress;
    private String phoneNumber;
    private BigDecimal price;
    private Set<EditPackageDTO> packages;
    private String specialKey;
}
