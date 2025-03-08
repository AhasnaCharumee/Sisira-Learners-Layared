package lk.ijse.gdse72.sisiralearners.bo;

import lk.ijse.gdse72.sisiralearners.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {

    }

    public static BOFactory getInstance() {
        if (boFactory == null) {
            return new BOFactory();
        }
        return boFactory;
    }

    public enum BOTypes {
        BOOKING, COURSE, EXAM, INSTRUCTOR, LOGIN, PAYMENT, SESSION, REGISTRATION, USER, VEHICLE
    }

    public SuperBO getBO(BOTypes boType) {
        switch (boType) {
            case BOOKING:
                return new BookingFormBOImpl();
            case COURSE:
                return new CourseFormBOImpl();
            case EXAM:
                return new ExamFormBOImpl();
            case INSTRUCTOR:
                return new InstructorFormBOImpl();
            case LOGIN:
                return new LoginFormBOImpl();
            case PAYMENT:
                return new PaymentFormBOImpl();
            case SESSION:
                return new SessionFormBOImpl();
            case REGISTRATION:
                return new StudentRegistrationFormBOImpl();
            case USER:
                return new UserFormBOImpl();
            case VEHICLE:
                return new VehicleFormBOImpl();
            default:
                return null;
        }
    }
}
