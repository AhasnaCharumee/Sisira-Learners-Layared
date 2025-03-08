package lk.ijse.gdse72.sisiralearners.dto.tm;

import java.sql.Date;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExamTM {
    String exam_id;
    String exam_name;
    String student_name;
    Date exam_date;
    String result;
}
