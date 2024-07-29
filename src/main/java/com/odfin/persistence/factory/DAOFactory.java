package com.odfin.persistence.factory;

import com.odfin.persistence.dao.MessageDAO;
import com.odfin.persistence.dao.UserDAO;

public abstract class DAOFactory {

    public static final int MY_SQL = 1;

    public abstract MessageDAO getMessageDAO();
    public abstract UserDAO getUserDAO();

    public static DAOFactory getDAOFactory(int factory){
        switch (factory){
            case MY_SQL :
                return new MySqlDAOFactory();
            default:
                return null;
        }
    }

}
