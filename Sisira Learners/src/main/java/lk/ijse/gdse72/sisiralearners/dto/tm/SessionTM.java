package lk.ijse.gdse72.sisiralearners.dto.tm;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionTM {
    private String session_id;
    private String instructor_name;
    private String vehicle_name;
    private String day;
    private String start_time;
    private String end_time;

}
