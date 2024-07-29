package com.odfin.facade;

import com.odfin.persistence.domain.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface UserFacade extends Remote {

    /**
     * Erstellt einen neuen Benutzer.
     *
     * @param user Der zu erstellende Benutzer
     * @throws RemoteException Bei Kommunikationsproblemen
     */
    void createUser(User user) throws RemoteException;

    /**
     * Holt einen Benutzer anhand seiner ID.
     *
     * @param userId Die ID des Benutzers
     * @return Der Benutzer
     * @throws RemoteException Bei Kommunikationsproblemen
     */
    User getUserById(Integer userId) throws RemoteException;

    /**
     * Holt alle Benutzer aus der Datenbank.
     *
     * @return Eine Liste aller Benutzer
     * @throws RemoteException Bei Kommunikationsproblemen
     */
    List<User> getAllUsers() throws RemoteException;

    /**
     * Aktualisiert einen bestehenden Benutzer.
     *
     * @param user Der zu aktualisierende Benutzer
     * @throws RemoteException Bei Kommunikationsproblemen
     */
    void updateUser(User user) throws RemoteException;

    /**
     * Löscht einen Benutzer anhand seiner ID.
     *
     * @param userId Die ID des zu löschenden Benutzers
     * @throws RemoteException Bei Kommunikationsproblemen
     */
    void deleteUser(Integer userId) throws RemoteException;
}
