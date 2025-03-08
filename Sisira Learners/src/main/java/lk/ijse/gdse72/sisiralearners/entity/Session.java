package lk.ijse.gdse72.sisiralearners.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Session {
    String session_id;
    String instructor_id;
    String vehicle_id;
    String day;
    String start_time;
    String end_time;
}
