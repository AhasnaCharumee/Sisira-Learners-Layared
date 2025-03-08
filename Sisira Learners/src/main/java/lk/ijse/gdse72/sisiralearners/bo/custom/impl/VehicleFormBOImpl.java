package lk.ijse.gdse72.sisiralearners.bo.custom.impl;

import lk.ijse.gdse72.sisiralearners.bo.custom.VehicleFormBO;
import lk.ijse.gdse72.sisiralearners.dao.DAOFactory;
import lk.ijse.gdse72.sisiralearners.dao.custom.VehicleDAO;
import lk.ijse.gdse72.sisiralearners.dto.VehicleDTO;
import lk.ijse.gdse72.sisiralearners.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleFormBOImpl implements VehicleFormBO {

    private VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.VEHICLE);

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
    public boolean saveVehicle(VehicleDTO vehicleDto) throws SQLException, ClassNotFoundException {
        Vehicle vehicle = new Vehicle(
                vehicleDto.getVehicle_id(),
                vehicleDto.getVehicle_name(),
                vehicleDto.getVehicle_number(),
                vehicleDto.getEngine_number(),
                vehicleDto.getVehicle_class(),
                vehicleDto.getStatus()
        );
        return vehicleDAO.save(vehicle);
    }

    @Override
    public boolean updateVehicle(VehicleDTO vehicleDto) throws SQLException, ClassNotFoundException {
        Vehicle vehicle = new Vehicle(
                vehicleDto.getVehicle_id(),
                vehicleDto.getVehicle_name(),
                vehicleDto.getVehicle_number(),
                vehicleDto.getEngine_number(),
                vehicleDto.getVehicle_class(),
                vehicleDto.getStatus()
        );
        return vehicleDAO.update(vehicle);
    }

    @Override
    public boolean deleteVehicle(String vehicleId) throws SQLException, ClassNotFoundException {
        return vehicleDAO.delete(vehicleId);
    }

    @Override
    public boolean existVehicle(String id) throws SQLException, ClassNotFoundException {
        return vehicleDAO.exist(id);
    }

    @Override
    public String getNextVehicleId() throws SQLException, ClassNotFoundException {
        return vehicleDAO.getNewId();
    }
}