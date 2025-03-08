package lk.ijse.gdse72.sisiralearners.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentRegistrationDTO {
    String registration_id;
    String student_id;
    String course_id;
    Date registration_date;
    String status;
}
