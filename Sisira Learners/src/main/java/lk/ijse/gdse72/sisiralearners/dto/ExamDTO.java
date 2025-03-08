package lk.ijse.gdse72.sisiralearners.dto;

import java.sql.Date;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExamDTO {
    String exam_id;
    String exam_name;
    String student_id;
    Date exam_date;
    String result;
}
