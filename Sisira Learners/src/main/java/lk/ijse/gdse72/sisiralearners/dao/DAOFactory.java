package lk.ijse.gdse72.sisiralearners.dao;

import lk.ijse.gdse72.sisiralearners.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getInstance() {
        if (daoFactory == null) {
            return new DAOFactory();
        }
        return daoFactory;
    }

    public enum DAOType {
        BOOKING,COURSE, EXAM, INSTRUCTOR, PAYMENT, QUERY, SESSION, STUDENT_REGISTRATION, STUDENT, USER, VEHICLE
    }

    public SuperDAO getDAO(DAOType daoType) {
        switch (daoType) {
            case BOOKING:
                return new BookingDAOImpl();
            case COURSE:
                return new CourseDAOImpl();


            case EXAM:
                return new ExamDAOImpl();
            case INSTRUCTOR:
                return new InstructorDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            case SESSION:
                return new SessionDAOImpl();
            case STUDENT_REGISTRATION:
                return new StudentRegistrationDAOImpl();
            case STUDENT:
                return new StudentDAOImpl();
            case USER:
                return new UserDAOImpl();
            case VEHICLE:
                return new VehicleDAOImpl();
            default:
                return null;
        }
    }
}
