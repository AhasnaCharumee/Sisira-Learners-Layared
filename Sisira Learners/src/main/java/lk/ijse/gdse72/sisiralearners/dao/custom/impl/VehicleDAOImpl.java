package lk.ijse.gdse72.sisiralearners.dao.custom.impl;

import lk.ijse.gdse72.sisiralearners.dao.SQLUtil;
import lk.ijse.gdse72.sisiralearners.dao.custom.VehicleDAO;
import lk.ijse.gdse72.sisiralearners.entity.Vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOImpl implements VehicleDAO {

    public ArrayList<Vehicle> getAllData() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Vehicle");
        ArrayList<Vehicle> vehicleDtos = new ArrayList<>();

        while (rst.next()) {
            Vehicle vehicleDto = new Vehicle(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)

            );
            vehicleDtos.add(vehicleDto);
        }

        return vehicleDtos;
    }

    public boolean save(Vehicle vehicle) throws SQLException {
        return SQLUtil.execute("INSERT INTO Vehicle VALUES (?,?,?,?,?,?)",
                vehicle.getVehicle_id(),
                vehicle.getVehicle_name(),
                vehicle.getVehicle_number(),
                vehicle.getEngine_number(),
                vehicle.getVehicle_class(),
                vehicle.getStatus()
        );
    }
    public boolean update(Vehicle vehicle) throws SQLException {
        return SQLUtil.execute("UPDATE Vehicle SET vehicle_name=?, vehicle_number=?, engine_number=?, vehicle_class=?, status=? WHERE vehicle_id=?",
                vehicle.getVehicle_name(),
                vehicle.getVehicle_number(),
                vehicle.getEngine_number(),
                vehicle.getVehicle_class(),
                vehicle.getStatus(),
                vehicle.getVehicle_id()
        );
    }

    public boolean delete(String vehicleId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Vehicle WHERE vehicle_id=?", vehicleId);
    }

    public boolean exist(String id) throws SQLException{
        ResultSet rst = SQLUtil.execute("SELECT vehicle_id FROM Vehicle WHERE vehicle_id=?", id);
        return rst.next();
    }
    public String getNewId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT vehicle_id FROM Vehicle ORDER BY vehicle_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("V%03d", newIdIndex);
        }
        return "V001";
    }

    public List<String> getAllVehicleNames() throws SQLException {
        List<String> vehicleNames = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT vehicle_name FROM Vehicle");
        while (resultSet.next()) {
            vehicleNames.add(resultSet.getString("vehicle_name"));
        }
        return vehicleNames;
    }
}
