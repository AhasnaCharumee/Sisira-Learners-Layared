package lk.ijse.gdse72.sisiralearners.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {
    String student_id;
    String name;
    String email;
    String nic;
    String contact;
    double pay_balance;
}
