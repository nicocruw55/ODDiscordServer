package com.odfin.persistence.dao;

import com.odfin.persistence.domain.Server;

import java.sql.SQLException;
import java.util.List;

public interface ServerDAO {

    Server getServerById(int serverId) throws SQLException;
    List<Server> getServersByUserID(int userID) throws SQLException;
    Server updateServer(Server server)throws SQLException;
    Server insertServer(Server server)throws SQLException;
    boolean deleteServer(int serverId)throws SQLException;

}
