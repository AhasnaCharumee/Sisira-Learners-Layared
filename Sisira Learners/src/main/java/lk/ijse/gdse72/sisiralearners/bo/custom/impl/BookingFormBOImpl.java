package lk.ijse.gdse72.sisiralearners.bo.custom.impl;

import lk.ijse.gdse72.sisiralearners.bo.custom.BookingFormBO;
import lk.ijse.gdse72.sisiralearners.dao.DAOFactory;
import lk.ijse.gdse72.sisiralearners.dao.custom.BookingDAO;
import lk.ijse.gdse72.sisiralearners.dao.custom.SessionDAO;
import lk.ijse.gdse72.sisiralearners.dao.custom.StudentDAO;
import lk.ijse.gdse72.sisiralearners.dto.BookingDTO;
import lk.ijse.gdse72.sisiralearners.dto.SessionDTO;
import lk.ijse.gdse72.sisiralearners.dto.StudentDTO;
import lk.ijse.gdse72.sisiralearners.entity.Booking;
import lk.ijse.gdse72.sisiralearners.entity.Session;
import lk.ijse.gdse72.sisiralearners.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingFormBOImpl implements BookingFormBO {

    private BookingDAO bookingDAO = (BookingDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BOOKING);
    private StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);
    private SessionDAO sessionDAO = (SessionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SESSION);

    @Override
    public ArrayList<BookingDTO> getAllBookings() throws SQLException, ClassNotFoundException {
        ArrayList<Booking> bookings = bookingDAO.getAllData();
        ArrayList<BookingDTO> bookingDTOs = new ArrayList<>();

        for (Booking booking : bookings) {
            bookingDTOs.add(new BookingDTO(
                    booking.getBooking_id(),
                    booking.getStudent_id(),
                    booking.getSession_id(),
                    booking.getBooking_date(),
                    booking.getPractise_session()
            ));
        }
        return bookingDTOs;
    }

    @Override
    public boolean saveBooking(BookingDTO bookingDto) throws SQLException, ClassNotFoundException {
        Booking booking = new Booking(
                bookingDto.getBooking_id(),
                bookingDto.getStudent_id(),
                bookingDto.getSession_id(),
                bookingDto.getBooking_date(),
                bookingDto.getPractise_session()
        );
        return bookingDAO.save(booking);
    }

    @Override
    public boolean updateBooking(BookingDTO bookingDto) throws SQLException, ClassNotFoundException {
        Booking booking = new Booking(
                bookingDto.getBooking_id(),
                bookingDto.getStudent_id(),
                bookingDto.getSession_id(),
                bookingDto.getBooking_date(),
                bookingDto.getPractise_session()
        );
        return bookingDAO.update(booking);
    }

    @Override
    public boolean deleteBooking(String bookingId) throws SQLException, ClassNotFoundException {
        return bookingDAO.delete(bookingId);
    }

    @Override
    public boolean existBooking(String id) throws SQLException, ClassNotFoundException {
        return bookingDAO.exist(id);
    }

    @Override
    public String getNextBookingId() throws SQLException, ClassNotFoundException {
        return bookingDAO.getNewId();
    }

    @Override
    public ArrayList<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException {
        ArrayList<Student> students = studentDAO.getAllData();
        ArrayList<StudentDTO> studentDTOs = new ArrayList<>();

        for (Student student : students) {
            studentDTOs.add(new StudentDTO(
                    student.getStudent_id(),
                    student.getName(),
                    student.getContact(),
                    student.getNic(),
                    student.getContact(),
                    student.getPay_balance()
            ));
        }
        return studentDTOs;
    }

    @Override
    public ArrayList<SessionDTO> getAllSessions() throws SQLException, ClassNotFoundException {
        ArrayList<Session> sessions = sessionDAO.getAllData();
        ArrayList<SessionDTO> sessionDTOs = new ArrayList<>();

        for (Session session : sessions) {
            sessionDTOs.add(new SessionDTO(
                    session.getSession_id(),
                    session.getInstructor_id(),
                    session.getVehicle_id(),
                    session.getDay(),
                    session.getStart_time(),
                    session.getEnd_time()
            ));
        }
        return sessionDTOs;
    }

    @Override
    public List<String> getAllStudentNames() throws SQLException {
        return studentDAO.getAllStudentNames();
    }
}