package lk.ijse.gdse72.sisiralearners.bo.custom;

import lk.ijse.gdse72.sisiralearners.bo.SuperBO;
import lk.ijse.gdse72.sisiralearners.dto.InstructorDTO;
import lk.ijse.gdse72.sisiralearners.dto.SessionDTO;
import lk.ijse.gdse72.sisiralearners.dto.VehicleDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SessionFormBO extends SuperBO {
    ArrayList<SessionDTO> getAllSessions() throws SQLException, ClassNotFoundException;
    boolean saveSession(SessionDTO sessionDTO) throws SQLException, ClassNotFoundException;
    boolean updateSession(SessionDTO sessionDTO) throws SQLException, ClassNotFoundException;
    boolean deleteSession(String sessionId) throws SQLException, ClassNotFoundException;
    boolean existSession(String id) throws SQLException, ClassNotFoundException;
    String getNextSessionId() throws SQLException, ClassNotFoundException;
    ArrayList<InstructorDTO> getAllInstructors() throws SQLException, ClassNotFoundException;
    List<String> getAllVehicleNames() throws SQLException;
    ArrayList<VehicleDTO> getAllVehicles() throws SQLException, ClassNotFoundException;

    List<String> getAllInstructorNames() throws SQLException;
}
