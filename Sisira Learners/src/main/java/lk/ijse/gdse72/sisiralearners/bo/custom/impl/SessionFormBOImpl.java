package lk.ijse.gdse72.sisiralearners.bo.custom.impl;

import lk.ijse.gdse72.sisiralearners.bo.custom.SessionFormBO;
import lk.ijse.gdse72.sisiralearners.dao.DAOFactory;
import lk.ijse.gdse72.sisiralearners.dao.custom.InstructorDAO;
import lk.ijse.gdse72.sisiralearners.dao.custom.SessionDAO;
import lk.ijse.gdse72.sisiralearners.dao.custom.VehicleDAO;
import lk.ijse.gdse72.sisiralearners.dto.InstructorDTO;
import lk.ijse.gdse72.sisiralearners.dto.SessionDTO;
import lk.ijse.gdse72.sisiralearners.dto.VehicleDTO;
import lk.ijse.gdse72.sisiralearners.entity.Instructor;
import lk.ijse.gdse72.sisiralearners.entity.Session;
import lk.ijse.gdse72.sisiralearners.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionFormBOImpl implements SessionFormBO {

    private SessionDAO sessionDAO = (SessionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SESSION);
    private InstructorDAO instructorDAO = (InstructorDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INSTRUCTOR);
    private VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.VEHICLE);

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
    public boolean saveSession(SessionDTO sessionDTO) throws SQLException, ClassNotFoundException {
        Session session = new Session(
                sessionDTO.getSession_id(),
                sessionDTO.getInstructor_id(),
                sessionDTO.getVehicle_id(),
                sessionDTO.getDay(),
                sessionDTO.getStart_time(),
                sessionDTO.getEnd_time()
        );
        return sessionDAO.save(session);
    }

    @Override
    public boolean updateSession(SessionDTO sessionDTO) throws SQLException, ClassNotFoundException {
        Session session = new Session(
                sessionDTO.getSession_id(),
                sessionDTO.getInstructor_id(),
                sessionDTO.getVehicle_id(),
                sessionDTO.getDay(),
                sessionDTO.getStart_time(),
                sessionDTO.getEnd_time()
        );
        return sessionDAO.update(session);
    }

    @Override
    public boolean deleteSession(String sessionId) throws SQLException, ClassNotFoundException {
        return sessionDAO.delete(sessionId);
    }

    @Override
    public boolean existSession(String id) throws SQLException, ClassNotFoundException {
        return sessionDAO.exist(id);
    }

    @Override
    public String getNextSessionId() throws SQLException, ClassNotFoundException {
        return sessionDAO.getNewId();
    }

    @Override
    public ArrayList<InstructorDTO> getAllInstructors() throws SQLException, ClassNotFoundException {
        ArrayList<Instructor> instructors = instructorDAO.getAllData();
        ArrayList<InstructorDTO> instructorDTOs = new ArrayList<>();

        for (Instructor instructor : instructors) {
            instructorDTOs.add(new InstructorDTO(
                    instructor.getInstructor_id(),
                    instructor.getName(),
                    instructor.getEmail(),
                    instructor.getContact(),
                    instructor.getVehicle_class()
            ));
        }
        return instructorDTOs;
    }

    @Override
    public List<String> getAllVehicleNames() throws SQLException {
        return vehicleDAO.getAllVehicleNames();
    }

    @Override
    public ArrayList<VehicleDTO> getAllVehicles() throws SQLException, ClassNotFoundException {
        ArrayList<Vehicle> vehicles = vehicleDAO.getAllData();
        ArrayList<VehicleDTO> vehicleDTOs = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            vehicleDTOs.add(new VehicleDTO(
                    vehicle.getVehicle_id(),
                    vehicle.getVehicle_name(),
                    vehicle.getVehicle_number(),
                    vehicle.getEngine_number(),
                    vehicle.getVehicle_class(),
                    vehicle.getStatus()
            ));
        }
        return vehicleDTOs;
    }

    @Override
    public List<String> getAllInstructorNames() throws SQLException {
        return instructorDAO.getAllInstructorNames();
    }
}