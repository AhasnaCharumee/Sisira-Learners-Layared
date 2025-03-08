package lk.ijse.gdse72.sisiralearners.dto.tm;

import lombok.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentTM {
    String payment_id;
    String student_id;
    String note;
    double amount;
    Date payment_date;
}
