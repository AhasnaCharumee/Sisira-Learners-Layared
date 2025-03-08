package lk.ijse.gdse72.sisiralearners.bo.custom;

import lk.ijse.gdse72.sisiralearners.bo.SuperBO;
import lk.ijse.gdse72.sisiralearners.dto.InstructorDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InstructorFormBO extends SuperBO {
    ArrayList<InstructorDTO> getAllInstructors() throws SQLException, ClassNotFoundException;
    boolean saveInstructor(InstructorDTO instructorDTO) throws SQLException, ClassNotFoundException;
    boolean updateInstructor(InstructorDTO instructorDTO) throws SQLException, ClassNotFoundException;
    boolean deleteInstructor(String instructorId) throws SQLException, ClassNotFoundException;
    boolean existInstructor(String id) throws SQLException, ClassNotFoundException;
    String getNextInstructorId() throws SQLException, ClassNotFoundException;
}
