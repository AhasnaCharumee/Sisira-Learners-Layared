package lk.ijse.gdse72.sisiralearners.entity;

import lombok.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Payment {
    String payment_id;
    String student_id;
    String note;
    double amount;
    Date payment_date;
}
