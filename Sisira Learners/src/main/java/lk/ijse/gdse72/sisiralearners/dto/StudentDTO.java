package lk.ijse.gdse72.sisiralearners.dto;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDTO {
    String student_id;
    String name;
    String email;
    String nic;
    String contact;
    double pay_balance;
}
