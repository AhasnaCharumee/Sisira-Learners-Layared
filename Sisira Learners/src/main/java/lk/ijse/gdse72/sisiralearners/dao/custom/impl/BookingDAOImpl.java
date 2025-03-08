package lk.ijse.gdse72.sisiralearners.dao.custom.impl;

import lk.ijse.gdse72.sisiralearners.dao.SQLUtil;
import lk.ijse.gdse72.sisiralearners.dao.custom.BookingDAO;
import lk.ijse.gdse72.sisiralearners.entity.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingDAOImpl implements BookingDAO {

    public ArrayList<Booking> getAllData() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Booking");
        ArrayList<Booking> bookings = new ArrayList<>();

        while (rst.next()) {
            Booking booking = new Booking(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getString(5)
            );
            bookings.add(booking);
        }

        return bookings;
    }

    public boolean save(Booking booking) throws SQLException {
        return SQLUtil.execute("INSERT INTO Booking VALUES (?,?,?,?,?)",
                booking.getBooking_id(),
                booking.getStudent_id(),
                booking.getSession_id(),
                booking.getBooking_date(),
                booking.getPractise_session()
        );
    }

    public boolean update(Booking booking) throws SQLException {
        return SQLUtil.execute("UPDATE Booking SET student_id=?, session_id=?, booking_date=?, practise_session=? WHERE booking_id=?",
                booking.getStudent_id(),
                booking.getSession_id(),
                booking.getBooking_date(),
                booking.getPractise_session(),
                booking.getBooking_id()
        );
    }

    public boolean delete(String bookingId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Booking WHERE booking_id=?", bookingId);
    }

    public boolean exist(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT booking_id FROM Booking WHERE booking_id=?", id);
        return rst.next();
    }

    public String getNewId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT booking_id FROM Booking ORDER BY booking_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("B%03d", newIdIndex);
        }
        return "B001";
    }

}