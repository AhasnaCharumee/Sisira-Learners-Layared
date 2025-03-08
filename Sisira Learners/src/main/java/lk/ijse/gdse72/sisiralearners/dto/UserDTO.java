package lk.ijse.gdse72.sisiralearners.dto;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class UserDTO {
    String user_id;
    String user_name;
    String email;
    String password;
    String role;
}
