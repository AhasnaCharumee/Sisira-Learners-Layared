package lk.ijse.gdse72.sisiralearners.dto.tm;

import java.util.Date;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingTM {
    String booking_id;
    String student_name;
    String practise_session;
    Date booking_date;
}