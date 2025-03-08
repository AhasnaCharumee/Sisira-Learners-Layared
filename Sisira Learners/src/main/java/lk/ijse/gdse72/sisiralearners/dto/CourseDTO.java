package lk.ijse.gdse72.sisiralearners.dto;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseDTO {
    String course_id;
    String status;
    String name;
    String duration;
    Double price;
}
