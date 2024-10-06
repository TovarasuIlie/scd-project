package SCD.Backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggedUserDTO {
    private Integer id;
    private String email;
    private String name;
    private String jwt;
    private String accessToken;
    private String refreshToken;
}
