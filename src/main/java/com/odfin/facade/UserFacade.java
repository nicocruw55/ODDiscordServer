package com.odfin.facade;

import com.odfin.persistence.domain.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface UserFacade extends Remote {

    User getUserById(Integer userId) throws RemoteException;
    List<User> getAllUsers() throws RemoteException;
    void updateUser(User user) throws RemoteException;
    void deleteUser(Integer userId) throws RemoteException;

}
