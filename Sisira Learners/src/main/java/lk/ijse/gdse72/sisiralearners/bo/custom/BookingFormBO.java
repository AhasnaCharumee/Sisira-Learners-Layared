package lk.ijse.gdse72.sisiralearners.bo.custom;

import lk.ijse.gdse72.sisiralearners.bo.SuperBO;
import lk.ijse.gdse72.sisiralearners.dto.BookingDTO;
import lk.ijse.gdse72.sisiralearners.dto.SessionDTO;
import lk.ijse.gdse72.sisiralearners.dto.StudentDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface BookingFormBO extends SuperBO {
    ArrayList<BookingDTO> getAllBookings() throws SQLException, ClassNotFoundException;
    boolean saveBooking(BookingDTO bookingDto) throws SQLException, ClassNotFoundException;
    boolean updateBooking(BookingDTO bookingDto) throws SQLException, ClassNotFoundException;
    boolean deleteBooking(String bookingId) throws SQLException, ClassNotFoundException;
    boolean existBooking(String id) throws SQLException, ClassNotFoundException;
    String getNextBookingId() throws SQLException, ClassNotFoundException;
    ArrayList<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException;
    ArrayList<SessionDTO> getAllSessions() throws SQLException, ClassNotFoundException;
    List<String> getAllStudentNames() throws SQLException;
}
