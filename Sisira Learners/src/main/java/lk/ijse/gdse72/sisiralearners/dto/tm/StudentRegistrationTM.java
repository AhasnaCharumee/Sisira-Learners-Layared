package lk.ijse.gdse72.sisiralearners.dto.tm;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRegistrationTM {
    String registration_id;
    String status;
    String student_name;
    String course_name;
    String student_email;
    String student_contact;
    double pay_balance;
}
