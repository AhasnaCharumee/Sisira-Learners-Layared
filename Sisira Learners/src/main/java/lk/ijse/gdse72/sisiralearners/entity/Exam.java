package lk.ijse.gdse72.sisiralearners.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Exam {
    String exam_id;
    String exam_name;
    String student_id;
    Date exam_date;
    String result;
}
