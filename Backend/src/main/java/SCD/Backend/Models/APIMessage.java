package SCD.Backend.Models;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class APIMessage {
    private HttpStatus httpStatus;
    private String message;

    public APIMessage(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
