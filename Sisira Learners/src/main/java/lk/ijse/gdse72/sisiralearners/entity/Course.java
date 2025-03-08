package lk.ijse.gdse72.sisiralearners.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Course {
    String course_id;
    String status;
    String name;
    String duration;
    Double price;
}
