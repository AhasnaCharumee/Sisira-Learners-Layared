package lk.ijse.gdse72.sisiralearners.dao.custom.impl;

import lk.ijse.gdse72.sisiralearners.dao.SQLUtil;
import lk.ijse.gdse72.sisiralearners.dao.custom.SessionDAO;
import lk.ijse.gdse72.sisiralearners.entity.Session;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SessionDAOImpl implements SessionDAO {

    public ArrayList<Session> getAllData() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Session");
        ArrayList<Session> sessions = new ArrayList<>();

        while (rst.next()) {
            Session session = new Session(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            sessions.add(session);
        }

        return sessions;
    }

    public boolean save(Session session) throws SQLException {
        return SQLUtil.execute("INSERT INTO Session VALUES (?,?,?,?,?,?)",
                session.getSession_id(),
                session.getInstructor_id(),
                session.getVehicle_id(),
                session.getDay(),
                session.getStart_time(),
                session.getEnd_time()
        );
    }

    public boolean update(Session session) throws SQLException {
        return SQLUtil.execute("UPDATE Session SET instructor_id=?, vehicle_id=?, day=?, start_time=?, end_time=? WHERE session_id=?",
                session.getInstructor_id(),
                session.getVehicle_id(),
                session.getDay(),
                session.getStart_time(),
                session.getEnd_time(),
                session.getSession_id()
        );
    }

    public boolean delete(String sessionId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Session WHERE session_id=?", sessionId);
    }

    public boolean exist(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT session_id FROM Session WHERE session_id=?", id);
        return rst.next();
    }

    public String getNewId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT session_id FROM Session ORDER BY session_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("SE%03d", newIdIndex);
        }
        return "SE001";
    }
}