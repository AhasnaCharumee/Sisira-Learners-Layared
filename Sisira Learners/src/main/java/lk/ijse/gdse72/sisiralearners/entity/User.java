package lk.ijse.gdse72.sisiralearners.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class User {
    String user_id;
    String user_name;
    String email;
    String password;
    String role;
}
