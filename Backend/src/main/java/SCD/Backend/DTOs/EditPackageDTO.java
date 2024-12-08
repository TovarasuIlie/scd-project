package SCD.Backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditPackageDTO {
    private Integer id;
    private String deliveryAddress;
    private String phoneNumber;
}
