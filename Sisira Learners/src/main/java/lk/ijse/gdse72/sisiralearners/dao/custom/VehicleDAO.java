package lk.ijse.gdse72.sisiralearners.dao.custom;

import lk.ijse.gdse72.sisiralearners.dao.CrudDAO;
import lk.ijse.gdse72.sisiralearners.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface VehicleDAO extends CrudDAO<Vehicle> {
    List<String> getAllVehicleNames() throws SQLException;
}
