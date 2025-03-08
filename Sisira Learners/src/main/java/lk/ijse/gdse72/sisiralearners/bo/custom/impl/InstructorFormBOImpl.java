package lk.ijse.gdse72.sisiralearners.bo.custom.impl;

import lk.ijse.gdse72.sisiralearners.bo.custom.InstructorFormBO;
import lk.ijse.gdse72.sisiralearners.dao.DAOFactory;
import lk.ijse.gdse72.sisiralearners.dao.custom.InstructorDAO;
import lk.ijse.gdse72.sisiralearners.dto.InstructorDTO;
import lk.ijse.gdse72.sisiralearners.entity.Instructor;

import java.sql.SQLException;
import java.util.ArrayList;

public class InstructorFormBOImpl implements InstructorFormBO {

    private InstructorDAO instructorDAO = (InstructorDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INSTRUCTOR);

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
    public boolean saveInstructor(InstructorDTO instructorDTO) throws SQLException, ClassNotFoundException {
        Instructor instructor = new Instructor(
                instructorDTO.getInstructor_id(),
                instructorDTO.getName(),
                instructorDTO.getEmail(),
                instructorDTO.getContact(),
                instructorDTO.getVehicle_class()
        );
        return instructorDAO.save(instructor);
    }

    @Override
    public boolean updateInstructor(InstructorDTO instructorDTO) throws SQLException, ClassNotFoundException {
        Instructor instructor = new Instructor(
                instructorDTO.getInstructor_id(),
                instructorDTO.getName(),
                instructorDTO.getEmail(),
                instructorDTO.getContact(),
                instructorDTO.getVehicle_class()
        );
        return instructorDAO.update(instructor);
    }

    @Override
    public boolean deleteInstructor(String instructorId) throws SQLException, ClassNotFoundException {
        return instructorDAO.delete(instructorId);
    }

    @Override
    public boolean existInstructor(String id) throws SQLException, ClassNotFoundException {
        return instructorDAO.exist(id);
    }

    @Override
    public String getNextInstructorId() throws SQLException, ClassNotFoundException {
        return instructorDAO.getNewId();
    }
}