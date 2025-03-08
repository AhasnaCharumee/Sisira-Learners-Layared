package lk.ijse.gdse72.sisiralearners.bo.custom;

import lk.ijse.gdse72.sisiralearners.bo.SuperBO;
import lk.ijse.gdse72.sisiralearners.dto.VehicleDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleFormBO extends SuperBO {
    ArrayList<VehicleDTO> getAllVehicles() throws SQLException, ClassNotFoundException;
    boolean saveVehicle(VehicleDTO vehicleDto) throws SQLException, ClassNotFoundException;
    boolean updateVehicle(VehicleDTO vehicleDto) throws SQLException, ClassNotFoundException;
    boolean deleteVehicle(String vehicleId) throws SQLException, ClassNotFoundException;
    boolean existVehicle(String id) throws SQLException, ClassNotFoundException;
    String getNextVehicleId() throws SQLException, ClassNotFoundException;
}
