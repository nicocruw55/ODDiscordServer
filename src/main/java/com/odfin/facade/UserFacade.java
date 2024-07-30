package com.odfin.facade;

import com.odfin.persistence.domain.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface UserFacade extends Remote {

    User getUserById(Integer userId) throws RemoteException;
    List<User> getAllUsers() throws RemoteException;
    List<User> getAllUsersFromChannel(Integer channelId) throws RemoteException;
    void updateUser(User user) throws RemoteException;
    void deleteUser(Integer userId) throws RemoteException;
    User login(String username, String password) throws RemoteException;

}
