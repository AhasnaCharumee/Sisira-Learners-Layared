package lk.ijse.gdse72.sisiralearners.dto;

import java.util.Date;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingDTO {
    String booking_id;
    String student_id;
    String session_id;
    Date booking_date;
    String practise_session;
}
