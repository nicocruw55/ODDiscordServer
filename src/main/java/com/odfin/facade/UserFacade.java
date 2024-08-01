package com.odfin.facade;

import com.odfin.persistence.domain.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface UserFacade extends Remote {

    User getUserById(int userId) throws RemoteException;
    List<User> getAllUsers() throws RemoteException;
    List<User> getAllUsersFromChannel(int channelId) throws RemoteException;
    void updateUser(User user) throws RemoteException;
    void deleteUser(int userId) throws RemoteException;
    User login(String username, String password) throws RemoteException;

}
