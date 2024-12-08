package SCD.Backend.DTOs;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PackageDTO {
    private String deliveryAddress;
    private boolean payOnDelivery;
    private String phoneNumber;
}
